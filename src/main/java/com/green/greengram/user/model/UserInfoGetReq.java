package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private long signedUserId;
    @Schema(name = "profile_user_id", description = "프로필 유저 PK",example = "2",requiredMode = Schema.RequiredMode.REQUIRED)
    private long profileUserId;

    public void setSignedUserId(long signedUserId) {
        this.signedUserId = signedUserId;
    }

    @ConstructorProperties({"profile_user_id"})
    public UserInfoGetReq(long signedUserId, long profileUserId) {
        this.profileUserId = profileUserId;
    }
}
