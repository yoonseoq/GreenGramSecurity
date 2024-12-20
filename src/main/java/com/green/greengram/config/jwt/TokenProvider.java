package com.green.greengram.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.green.greengram.config.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.*;

@Service // 빈등록을 했기 때문에 빈, 그리고 생성자도 따로 만들어짐
public class TokenProvider {
    private final ObjectMapper objectMapper; //jackson 라이브러리
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public TokenProvider(JwtProperties jwtProperties, ObjectMapper objectMapper) {
        /*
            의존성이란 외부에서 주소값 들어오는것
            생성자 세터 필드 이 세가지로 주입

         */
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
    }



    //jwt 토큰 생성
    public String generateToken(JwtUser jwtUser, Duration expiredAt) {
        Date now = new Date();
        return makeToken(jwtUser, new Date(now.getTime() + expiredAt.toMillis()));
    }//                                    지금까지 한것    만료된 시간

    private String makeToken(JwtUser jwtUser, Date expiry) {
        // 객체 자체를 JWT 에 담고 싶어서 객체를 직렬화
        // JwtUser 에 담고있는 데이터를 JSON 형태의 문자열로 변환
        // jwt 암호화
        JwtBuilder builder = Jwts.builder();
        JwtBuilder.BuilderHeader header = builder.header();
        header.type("JWT");

        builder.issuer(jwtProperties.getIssuer());

        return Jwts.builder()
                .header().type("JWT")
                .and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(expiry)
                .claim("signedUser", makeClaimByUserToString(jwtUser))
                .signWith(secretKey)
                .compact();
    }
    //유일하게 객체가 들어갈수 있는 부분을 스트링밖에 없다

    private String makeClaimByUserToString(JwtUser jwtUser) {
        try {
            return objectMapper.writeValueAsString(jwtUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validToken(String token) {

        //jwt 복호화
        try {
            getClaims(token);

        } catch (Exception e) {
            return false;
        }
            return true;
    }

    // spring security 에서 인증처리를 해줘야 한다. 그때 authentication 객체가 필요
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token);

        /*
            Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER));
            권한 인가 부분. set의 특징은 이 객체 주소값만 담을 수 있다. 같은 주소값을 여러개 담을수 있다. set은 중복제거의 역할을 가지고 있다
            어떤사용자라도 권한은 유저만 가지고 있다
            return new UsernamePasswordAuthenticationToken()
        */

        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //옛날에 아이디 비번으로 인증처리 할때 쓰던거
    }

    public JwtUser getJwtUser(String token) {
        Claims claims = getClaims(token);
        String json = (String) claims.get("signedUser");
        JwtUser jwtUser = null;
        try {
            jwtUser = objectMapper.readValue(json,JwtUser.class);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jwtUser;
    }

    public UserDetails getUserDetailsFromToken(String token) {
        JwtUser jwtUser = getJwtUser(token);
        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setJwtUser(jwtUser);
        return userDetails;
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}


