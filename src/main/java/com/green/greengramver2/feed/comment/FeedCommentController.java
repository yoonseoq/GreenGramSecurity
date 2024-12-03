package com.green.greengramver2.feed.comment;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.comment.model.FeedCommentDelReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
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
    // 쿼리스트링
    @GetMapping
    @Operation(summary = "피드 댓글 리스트", description = "댓글 더보기 처리")
    // 리퀘스트 파람은 컨트롤러에다 받음
    // @ModelAttribute 이걸 안쓰면 각 값을 일일이 넣어야 한다
    public ResultResponse<FeedCommentGetRes> getFeedCommentList(@ParameterObject @ModelAttribute FeedCommentGetReq p){
       FeedCommentGetRes list = service.getFeedComment(p);
        return ResultResponse.<FeedCommentGetRes>builder()
                .resultMessage(String.format("%d rows",list.getCommentList().size()))
                .resultData(list)
                .build();
    }

    // 삭제시 받아야 할 데이터 feedCommentId 로그인한 사용자의 pk (feed_comment_id, )
    // 쿼리스트링
    @DeleteMapping
    public ResultResponse<Integer> delFeedComment(@ModelAttribute FeedCommentDelReq p){
       int del =service.delFeedComment(p);
       return ResultResponse.<Integer>builder()
               .resultMessage("댓삭")
               .resultData(del).build();
    }

}
