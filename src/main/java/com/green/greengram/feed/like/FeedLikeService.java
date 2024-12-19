package com.green.greengram.feed.like;

import com.green.greengram.common.config.security.AuthenticationFacade;
import com.green.greengram.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class FeedLikeService {
    private final FeedLikeMapper mapper;
    private final AuthenticationFacade authenticationFacade;
     public int  feedLikeToggle(FeedLikeReq p){
         p.setUserId(authenticationFacade.getSignedUserId());
         int result = mapper.delFeedLike(p); // 삭제
         if(result == 0){
             return mapper.insFeedLike(p); // 좋아요 등록되면 1 보내줌
         }
         return 0;
     }
}
