package com.green.greengramver2.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final String uploadPath;

    public WebMvcConfiguration(@Value("${file.directory}")String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 우리가 가지고 있는  자료를 핸들링해서 추가한다는 의미.
        registry.addResourceHandler("/pic/**")
                // 이 의미는 요청경로가 /pic/** 인 URL 에 대해 정적 리소스를 제공할 위치를 지정
                .addResourceLocations("file:" + uploadPath + "/");
        // 실제 파일 시스템에서 리소스가 위치한 경로를 지정한다.
        // uploadPath 는 와부설정 하일에서 file,directory 값으로 주입받은 경로이다
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // RestController의 모든 URL에 "/api" prefix를 설정
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
        // 자동으로 api붙음
    }
}