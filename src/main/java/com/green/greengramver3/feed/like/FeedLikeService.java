package com.green.greengramver3.feed.like;

import com.green.greengramver3.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class FeedLikeService {
    private final FeedLikeMapper mapper;
     public int  feedLikeToggle(FeedLikeReq p){
         int result = mapper.delFeedLike(p); // 삭제
         if(result == 0){
             return mapper.insFeedLike(p); // 좋아요 등록되면 1 보내줌
         }
         return 0;
     }
}
