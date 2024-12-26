package com.green.greengram.feed.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedAndPicDto {
    private long feedId;
    private String contents;
    private String location;
    private String createdAt;
    private long writerUserId;
    private String writerNm;
    private String writerPic;
    private int isLike;

    private String pic;
}
