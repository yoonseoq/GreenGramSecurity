package com.green.greengram.common.exception;

import com.green.greengram.common.model.ResultResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.validation.FieldError;

import java.util.List;

@Setter
@SuperBuilder
public class MyResultResponse extends ResultResponse<String> {
    //Validation 에러메세지 전달

    private final List<ValidationError> valids;

    /*
    ValidationError 가 발생시 해당 에러의 메세지
    어떤 필드였고 에러메세지를 묶음으로 담을 객체를 만들 때 사용
     */
    @Getter
    @Builder // 빌더패턴 객체화
    public static class ValidationError {
        private final String field;
        private final String message;

            public static ValidationError of(final FieldError fieldError) {
                return ValidationError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build();
            }
    }
}
