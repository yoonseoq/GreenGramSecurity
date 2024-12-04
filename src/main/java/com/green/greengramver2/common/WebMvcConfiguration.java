package com.green.greengramver2.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//빈등록 되어있는 애들은 component 상속됨
//@Bean 을 메소드위에 놓으면
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final String uploadPath;
    //final 붙으면 setter 안됨 값넣는 방법은  생성자나
    //이건 생성자임 무슨값을 넣어야 하는지 @Value("${file.directory}") 이거를 넣어줘야한다
    public WebMvcConfiguration(@Value("${file.directory}")String uploadPath) {

        this.uploadPath = uploadPath;
    }
    //@Autowired

    //@Bean 여기 달려있으면 싱글톤으로 리턴

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 우리가 가지고 있는  자료를 핸들링해서 추가한다는 의미
        // 주소값이 pic 으로 들어오면 이런식으로 수정이 된다.
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