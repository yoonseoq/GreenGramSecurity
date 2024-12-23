package com.green.greengram.feed.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeedCommentGetRes {
    @Schema(title = "피드 댓글 더보기 여부")
    private boolean moreComment; //더 있는지 아닌지
    @Schema(title = "피드 댓글 리스트")
    private List<FeedCommentDto> commentList; //일단 3개의 댓글만 담김
    // 댓글 객체를 여러개 담을 수 있는 ArrayList


}
