package com.green.greengramver3.feed.like;

import com.green.greengramver3.common.model.ResultResponse;
import com.green.greengramver3.feed.like.model.FeedLikeReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("feed/like")
@RequiredArgsConstructor
@Tag(name = "3. 피드 좋아요",description = "피드 좋아요 토글")
public class FeedLikeController {
    private final FeedLikeService service;

    @GetMapping
    // 원래 post 쓰는데 get 이 더 빨라서
    @Operation(summary = "피드좋아요", description = "토글처리")
    public ResultResponse<Integer> feedLikeToggle(@ParameterObject @ModelAttribute FeedLikeReq p){
        log.info("FeedLikeController > feedLikeToggle > p: {}",p);
        int result = service.feedLikeToggle(p);
        return ResultResponse.<Integer>builder().resultMessage(result == 0?"좋아요 취소":"좋아요 등록").resultData(result).build();
    }
}
