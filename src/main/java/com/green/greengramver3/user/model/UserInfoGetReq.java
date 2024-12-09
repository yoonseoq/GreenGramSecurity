package com.green.greengramver3.user.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;
@Setter
@Getter
@Schema(title = "유저 정보 Get요청")
@ToString(callSuper = true)
public class UserInfoGetReq {
    @Schema(name = "signed_user_id", description = "로그인한 유저 PK",example = "2",requiredMode = Schema.RequiredMode.REQUIRED)
    private long signedUserId;
    @Schema(name = "profile_user_id", description = "프로필 유저 PK",example = "2",requiredMode = Schema.RequiredMode.REQUIRED)
    private long profileUserId;

    @ConstructorProperties({"signed_user_id","profile_user_id"})
    public UserInfoGetReq(long signedUserId, long profileUserId) {
        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
    }
}
