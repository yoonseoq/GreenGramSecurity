package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedPostReq {
    @JsonIgnore
    private long feedId;
    @JsonIgnore // 토큰에 데이터가 들어가있어서 굳이
    private long  writerUserId;

    @Size(max = 1000, message = "내용은 1,000자 이하만 가능")
    @Schema(title = "피드내용",example = "피드내용 테스트")
    private String contents;

    @Size(max = 30, message = "위치는 30자 이하만 가능")
    @Schema(title = "피드위치",example = "그린 컴퓨터 학원")
    private String location;


}
