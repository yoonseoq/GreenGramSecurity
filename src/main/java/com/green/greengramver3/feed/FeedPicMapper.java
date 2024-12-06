package com.green.greengramver3.feed;

import com.green.greengramver3.feed.model.FeedPicDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicMapper {
    int insFeedPicList(FeedPicDto p);
    List<String> selFeedPicList(long feedId);
}
