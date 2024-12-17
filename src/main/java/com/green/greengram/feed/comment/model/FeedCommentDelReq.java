package com.green.greengram.feed.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class FeedCommentDelReq {
    @Schema(name = "feed_comment_id")
    private long feedCommentId;
    @Schema(name = "signed_user_id")
    private long userId;

    @ConstructorProperties({"feed_comment_id","signed_user_id"})
    public FeedCommentDelReq(long feedCommentId, long userId) {
        this.feedCommentId = feedCommentId;
        this.userId = userId;
    }
}
