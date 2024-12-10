package com.green.greengramver3.feed.comment.model;

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
