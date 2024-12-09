package com.green.greengramver3.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class UserPicPatchReq {
    private long signedUserId;
    private MultipartFile pic;

    @JsonIgnore
    private String picName;
}
