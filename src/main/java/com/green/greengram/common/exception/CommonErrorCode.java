package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
/*
500단위 클라이언트측 에러
400단위 서버측 에러

401 리프레시 토큰이 있다면 재발행가능
 */
@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{
    // enum 쓰는이유 잘못된거를 바로잡음
    // 우아한 형제들 기술블로그 가보면 잘 나와있음

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버내부에 에러가 발생하였습니다")
    , INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"잘못된 파라미터 입니다");

 //어레 터트리면 GlobalException 어쩌구가 잡아줌

    private final HttpStatus httpStatus;
    private final String message;

}
