package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    INCORRECT_ID_PW("아이디, 비밀번호를 확인해주세요.")
    ,USER_NOT_FOUND("유저를 찾을 수 없습니다")
    ,
    ;
    private final String message;
}
