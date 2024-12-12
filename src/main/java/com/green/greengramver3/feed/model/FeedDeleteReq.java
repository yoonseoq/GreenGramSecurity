package com.green.greengramver3.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
@Schema(title = "피드 DELETE 요청")
public class FeedDeleteReq {
    @Schema(description = "피드 PK", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    @Schema(description = "로그인 유저 PK", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long signedUserId;

    @ConstructorProperties({"feed_id", "signed_user_id"})
    public FeedDeleteReq(long feedId, long signedUserId) {
        this.feedId = feedId;
        this.signedUserId = signedUserId;
    }
}
