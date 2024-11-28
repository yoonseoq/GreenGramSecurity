package com.green.greengramver2.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "로그인")
public class UserSignInReq {
    private String uid;
    private String upw;
}
