package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedPostReq {
    @JsonIgnore
    private long feedId;
    @JsonIgnore // 토큰에 데이터가 들어가있어서 굳이
    private long  writerUserId;
    @Schema(title = "피드내용",example = "피드내용 테스트")
    private String contents;

    @Schema(title = "피드위치",example = "그린 컴퓨터 학원")
    private String location;


}
