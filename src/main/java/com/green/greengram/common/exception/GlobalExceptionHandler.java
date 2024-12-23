package com.green.greengram.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice //AOP (Aspect Orientation Programming, 관점지향 프로그래밍)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //우리가 커스텀한 예외가 발생되었을 경우 캐치
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> handleCustomException(final CustomException e) {
        log.error("CustomException - ", e);
        return handleExceptionInternal(e.getErrorCode());
    }

    //Validation 에외가 발생되었을 경우 캐치
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request){
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER,ex);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleBindException() {
        return handleExceptionInternal(UserErrorCode.UNAUTHENTICATED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedException() {
        return handleExceptionInternal(UserErrorCode.INVALID_TOKEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredException() {
        return handleExceptionInternal(UserErrorCode.EXPIRED_TOKEN);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return handleExceptionInternal(errorCode, null);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode,BindException e) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                             .body(makeErrorResponse(errorCode, e));
    }

    private MyErrorResponse makeErrorResponse(ErrorCode errorCode, BindException e) {
        //resultResponse 상속받음
        return MyErrorResponse.builder()
                .resultMessage(errorCode.getMessage())
                .resultData(errorCode.name())
                .valids(e == null ? null : getValidationErrors(e))
                .build();
    }


    private List<MyErrorResponse.ValidationError> getValidationErrors(BindException e) {
        List<FieldError> fieldErrors =e.getBindingResult().getFieldErrors();

        List<MyErrorResponse.ValidationError> errors = new ArrayList<>(fieldErrors.size());
        for(FieldError fieldError : fieldErrors){
            errors.add(MyErrorResponse.ValidationError.of(fieldError));
        }
        return errors;
    }
}
//@Transactional