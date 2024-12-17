package com.green.greengramver3.common.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengramver3.common.config.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
public class TokenProvider {
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public TokenProvider(JwtProperties jwtProperties, ObjectMapper objectMapper) {
        // di 주입하기

        this.objectMapper = objectMapper;
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
    }

    public String generateToken(JwtUser jwtUser, Duration expiredAt) {
        Date now = new Date();
        return makeToken(jwtUser, new Date(now.getTime() + expiredAt.toMillis()));
    }

    private String makeToken(JwtUser jwtUser, Date expiry) {

        return Jwts.builder().header().add("typ","JWT").add("alg","HS256")
                .and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(expiry)
                .claim("signedUser",makeClaimByUserToString(jwtUser))
                .signWith(secretKey)
                .compact();
    }

    private Object makeClaimByUserToString(JwtUser jwtUser) {
        try{
            return objectMapper.writeValueAsString(jwtUser);
        }catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public boolean verifyToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetails(token);

        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public UserDetails getUserDetails(String token) {
        Claims claims = getClaims(token);
        String json = (String) claims.get("signedUser");
        JwtUser jwtUser = null;
        try {
            jwtUser = objectMapper.readValue(json, JwtUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setJwtUser(jwtUser);

        return userDetails;
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token).getBody();
    }
}
