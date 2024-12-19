package com.green.greengram.feed.like.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedLikeReq {

    private long feedId;
    @JsonIgnore
    private long userId;
}
