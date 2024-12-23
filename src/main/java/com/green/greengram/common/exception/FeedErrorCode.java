package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum FeedErrorCode implements ErrorCode {
    ;


    private final HttpStatus status;
    private final String message;

    FALE_TO_RAG(HttpStatus.INTERNAL_SERVER_ERROR,"피드 어쩌구");


    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }
}
