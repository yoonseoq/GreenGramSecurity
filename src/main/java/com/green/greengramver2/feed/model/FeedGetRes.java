package com.green.greengramver2.feed.model;

import com.green.greengramver2.feed.comment.model.FeedCommentGetRes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class FeedGetRes {
    private long feedId;
    private String contents;
    private String location;
    private String createdAt;
    private long writerUserId;
    private String writerNm;
    private String writerPic;
    private int isLike;

    private List<String> pics ;
    private FeedCommentGetRes comment;
    //댓글관련정보 레퍼런스 변수 FeedCommentGetRes 의 주소값이 들어간다 즉 객체화함
}
