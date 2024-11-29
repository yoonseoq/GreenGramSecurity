package com.green.greengramver2.like.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedLikeReq {

    private long feedId;
    private long userId;
}
