package com.green.greengramver2.feed.comment;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("feed/comment")
@Slf4j
public class FeedCommentController {
    private final FeedCommentService service;
    @PostMapping
    public ResultResponse<Long> postFeedComment(@RequestBody FeedCommentPostReq p){
        return ResultResponse.<Long>builder()
                .resultMessage("코멘트등록완료")
                .resultData(service.postFeedComment(p))
                .build();
    }

}
