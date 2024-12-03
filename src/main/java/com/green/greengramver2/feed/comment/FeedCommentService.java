package com.green.greengramver2.feed.comment;

import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedCommentService {
    private final FeedCommentMapper mapper;

    public long postFeedComment(FeedCommentPostReq p){
        return mapper.insFeedComment(p);
    }
}
