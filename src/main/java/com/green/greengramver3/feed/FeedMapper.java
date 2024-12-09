package com.green.greengramver3.feed;

import com.green.greengramver3.feed.model.FeedGetReq;
import com.green.greengramver3.feed.model.FeedGetRes;
import com.green.greengramver3.feed.model.FeedPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedPostReq p);
    List<FeedGetRes> selFeedList(FeedGetReq p);

}
