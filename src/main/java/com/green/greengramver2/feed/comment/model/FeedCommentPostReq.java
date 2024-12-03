package com.green.greengramver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(title = "피드 댓글 등록 요청")
public class FeedCommentPostReq {
    @Schema(title = "유저 PK", example = "1",requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;
    @Schema(title = "피드 PK", example = "1",requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    @Schema(title = "댓글", example = "blabla",requiredMode = Schema.RequiredMode.REQUIRED)
    private String comment;
    // private long CommentId; 이건 아마도 auto_increment

    @JsonIgnore
    private long commentId;


}
