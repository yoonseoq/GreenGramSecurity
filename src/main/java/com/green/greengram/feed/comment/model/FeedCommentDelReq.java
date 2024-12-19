package com.green.greengram.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class FeedCommentDelReq {
    @Schema(name = "feed_comment_id")
    private long feedCommentId;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @JsonIgnore
    private long userId;

    @ConstructorProperties({"feed_comment_id"})
    public FeedCommentDelReq(long feedCommentId) {
        this.feedCommentId = feedCommentId;
    }
}
