package com.green.greengram.feed.comment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FeedCommentSel {
    private long feedId;
    private String comment;
}
