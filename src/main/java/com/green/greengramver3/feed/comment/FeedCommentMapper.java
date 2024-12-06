package com.green.greengramver3.feed.comment;

import com.green.greengramver3.feed.comment.model.FeedCommentDelReq;
import com.green.greengramver3.feed.comment.model.FeedCommentDto;
import com.green.greengramver3.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver3.feed.comment.model.FeedCommentPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    int insFeedComment(FeedCommentPostReq p); // 댓글달기
    List<FeedCommentDto> selFeedCommentList(FeedCommentGetReq p); //댓글조회
    int delFeedComment(FeedCommentDelReq p); //댓글삭제D
}