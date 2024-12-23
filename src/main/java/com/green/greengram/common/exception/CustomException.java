package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
    // 우리가 접하는 런타임 에러는 다 RuntimeException 얘가 터트려 주는거임
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    // 모든 에러코드를 잡아줌
}
