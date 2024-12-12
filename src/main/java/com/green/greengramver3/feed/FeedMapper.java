package com.green.greengramver3.feed;

import com.green.greengramver3.feed.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedPostReq p);
    List<FeedGetRes> selFeedList(FeedGetReq p);
    int delFeedLikeAndFeedCommentAndFeedPic(FeedDeleteReq p);
    int delFeed(FeedDeleteReq p);

}
