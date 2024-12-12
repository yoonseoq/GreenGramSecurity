package com.green.greengramver3.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FeedCommentDto {
    private long feedCommentId;
    private String comment;
    private long writerUserId;
    private String writerNm;
    private String writerPic;
   @JsonIgnore
    private long feedId;

}
