package com.green.greengramver2.feed.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeedCommentGetRes {

    private boolean moreComment; //더 있는지 아닌지
    private List<FeedCommentDto> commentList; //일단 3개의 댓글만 담김
    // 댓글 객체를 여러개 담을 수 있는 ArrayList


}
