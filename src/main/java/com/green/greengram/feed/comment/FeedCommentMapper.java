package com.green.greengram.feed.comment;

import com.green.greengram.feed.comment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    int insFeedComment(FeedCommentPostReq p); // 댓글달기
    List<FeedCommentDto> selFeedCommentList(FeedCommentGetReq p); //댓글조회
    int delFeedComment(FeedCommentDelReq p); //댓글삭제D
    List<FeedCommentDto> selCommentListByFeedId(List<Long> feedIds);
}
