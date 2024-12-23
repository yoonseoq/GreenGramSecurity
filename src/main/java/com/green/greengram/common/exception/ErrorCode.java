package com.green.greengram.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    String getMessage(); // 나를 상속받은 ENUM 은 String massage 멤버필드를 꼭 가져야 한다
    HttpStatus getHttpStatus(); //응답을 할대 코드 200 401 403 으로 리턴할지
    // accessToken 만료시 401 즉 응답코드결정 멤버필드
}
