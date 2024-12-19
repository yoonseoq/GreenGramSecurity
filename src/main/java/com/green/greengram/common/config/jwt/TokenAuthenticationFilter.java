package com.green.greengram.common.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            log.info("ip address: {}", request.getRemoteAddr());
            String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
            log.info("AuthorizationHeader: {}", authorizationHeader);
            // 토근값만 얻가우ㅣ해서 substring 사용

            String token = getAccessToken(authorizationHeader);
            log.info(" token: {}", token);

            if (tokenProvider.validToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        }

    private String getAccessToken(String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            //null 이 아니어야 하고 문자열이 Bearer 로 시작하는가
        return authorizationHeader.substring(TOKEN_PREFIX.length()); //7번 인덱스부터 끝까지 문자열을 잘라서 리턴. 그 앞에값 제외하고
        }
        return null;
        // 아니면 null 로 날라감
    }
}
