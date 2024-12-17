package com.green.greengramver3.common.config.security;
// Spring Security 세팅

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration // 메소드 빈등록이 있어야 의미가 있다. 메소드 빈등록이 싱글톤이 됨
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Bean // 스프링이 메소드 호출을 하고 리턴한 객체의 주소값을 관리한다(빈등록)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 시큐리티가 세션을 사용하지 않는다.
                .httpBasic(h->h.disable()) //ssr이 아니다. 화면을 만들지 않을거기 때문에 비활성화 시킨다. 시큐리티 로그인창 나타나지 않을 것이다.
                .formLogin(form -> form.disable())//폼로그인 기능 자체를 비활성화
                .csrf(csrf -> csrf.disable()) //ssr이 아니다. 보안관련
                .authorizeHttpRequests(req ->req.requestMatchers("/api/feed","/api/feed/ver3","/api/").authenticated()
                        .anyRequest().permitAll() //나머지 요청은 모두 허용
                )
                .build();
    }
}
                /* 람다식은 뭐하는 애임? 메소드를 하나의 식으로 표현하는것
                    익명클래스를 객체화 하는것
                    인터페이스를 임플리먼트 할라묜 클래스를 만들어야 하고 그 인터페이스의 메소드를 구현하고
                    new 어쩌구 해야함 이거 너무 귀찮음
                    람다식은 메소드 하나가 꼭 있어야 한다.
                    그래서 @FunctionalInterface 가 있어야 한다
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 스프링 시큐리티 기능 비활성화(스프링 시큐리티가 관여하지 않았으면 하는 부분)
        return web -> web.ignoring()
                                    .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }
                 */
