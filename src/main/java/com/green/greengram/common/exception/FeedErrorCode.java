package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FeedErrorCode implements ErrorCode {


    FAIL_TO_RAG(HttpStatus.INTERNAL_SERVER_ERROR,"피드 등록에 실패 어쩌구");

    private final HttpStatus httpStatus;
    private final String message;



}
