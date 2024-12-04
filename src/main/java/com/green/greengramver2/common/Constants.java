package com.green.greengramver2.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 빈등록 spring container 가 갹체생성
@Component
// 스프링에 bean 의존성 주입 DI? 빈등록하고 스프링이 리소스를 관리한다.
public class Constants {
    @Getter
    private static int default_page_size;

    // @Value 이 애노테이션은  properties 에 작성된 키 값을 ${}안에 넣어주면
    // Spring 이 PropertyPlaceHolderConfigurer 를 통해 초기화 작업 중에 해당 값을 실제 값으로 치환

    public Constants(@Value("${const.default-page-size}")int default_page_size) {
        Constants.default_page_size = default_page_size;
    }
}
