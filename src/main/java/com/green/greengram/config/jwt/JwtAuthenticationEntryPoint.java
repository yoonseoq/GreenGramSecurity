package com.green.greengram.config.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
// security 에 등록 필요
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    //@Qualifier 스프링 컨테이너로 DI 했을떄 여러빈 중 하나를 선택할 수 있음. DI 값을 적으면 됨
    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request
                        , HttpServletResponse response
                        , AuthenticationException authException) throws IOException, ServletException {
        resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
    }
    /*
        (Exception) request.getAttribute("exception")); 여기서 왜 get 일까
        어딘가 세팅되었기 떄문
     */
}
