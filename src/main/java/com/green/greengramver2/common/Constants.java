package com.green.greengramver2.common;

import org.springframework.stereotype.Component;

@Component // 빈등록 spring container가 갹체생성
// @value yaml에 있는 데이터를 건네준다 이 애노테이션 붙는 순간 그 메소드 무조건 부름
// 20값을 리턴할 수 있다.
public class Constants {





public final int DEFAULT_PAGE_SIZE = 20;
}
