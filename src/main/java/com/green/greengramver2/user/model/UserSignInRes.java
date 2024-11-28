package com.green.greengramver2.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Schema(title="로그인 응답")
public class UserSignInRes {
    private long userId;
    private String nickName;
    private String pic;
    @JsonIgnore // 스웨거에 표시는 안되지만, 응답때 빼는 역할
    private String upw;
    @JsonIgnore
    private String message;
}
// 리턴으로 줄 수 있는 데이터는 하나뿐