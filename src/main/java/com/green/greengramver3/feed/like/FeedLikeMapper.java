package com.green.greengramver3.feed.like;

import com.green.greengramver3.feed.like.model.FeedLikeReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedLikeMapper {
    int insFeedLike(FeedLikeReq p);
    int delFeedLike(FeedLikeReq p);
}