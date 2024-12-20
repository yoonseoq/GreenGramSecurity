package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INTERNAL_SERVER_ERROR("서버내부에 에러가 발생하였습니다")
    , INVALID_PARAMETER("잘못된 파라미터 입니다");


    private final String message;
}
