package com.green.greengram.common.config.jwt;

import com.green.greengram.common.config.jwt.JwtUser;
import com.green.greengram.common.config.jwt.TokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {
    // 테스트때는 생성자를 이용한 DI가 불가능
    // di 방법은 필드, setter, 생성자.\
    // 테스트 때는 필드주입방식을 사용하낟

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    public void generateToken() {
        //Given (준비 단계)
        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(10);

        List<String> roles = new ArrayList<>(2);
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        jwtUser.setRoles(roles);

        //When (실행 단계)
        String token = tokenProvider.generateToken(jwtUser, Duration.ofHours(3));
        //1분 설정
        //Then (검증 단계)
        assertNotNull(token);

        System.out.println("token: " + token);

    }

    @Test
    void validateToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJncmVlbkBncmVlbi5rciIsImlhdCI6MTczNDQwMTg3MywiZXhwIjoxNzM0NDAxOTMzfQ.cs_LqrXh-axzljXX6WAvuNAs9L0utAEcKc0w6PM62rA";

        boolean result = tokenProvider.verifyToken(token);

        assertFalse(result);
    }

    @Test
    void getAuthentication() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJncmVlbkBncmVlbi5rciIsImlhdCI6MTczNDQwNDg5NSwiZXhwIjoxNzM0NDE1Njk1LCJzaWduZWRVc2VyIjoie1wic2lnbmVkVXNlcklkXCI6MTAsXCJyb2xlc1wiOltcIlJPTEVfVVNFUlwiLFwiUk9MRV9BRE1JTlwiXX0ifQ.ZuiEzqCmV_UCz7nw6Zxr8JpRackNMafSsMffOximqgHFNjHcnxmfwu-zzGgsikchOrYcEjKAgMhdoCzV4DngGw"; //3시간따리

        Authentication authentication = tokenProvider.getAuthentication(token);

        assertNotNull(authentication);
    }

}