package com.green.greengramver2.feed;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.model.FeedGetReq;
import com.green.greengramver2.feed.model.FeedGetRes;
import com.green.greengramver2.feed.model.FeedPostReq;
import com.green.greengramver2.feed.model.FeedPostRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequestMapping("feed")
@RestController
@Slf4j

public class FeedController {
    private final FeedService service;

    @PostMapping
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics,
                                                @RequestPart FeedPostReq p) {
        log.info("FeedController > getFeedList > p : {}",p);
        FeedPostRes res = service.postFeed(pics, p);
        List<FeedGetRes> list = new ArrayList<>();
        return ResultResponse.<FeedPostRes>builder()
                .resultMessage("피드등록완료")
                .resultData(res)
                .build();
    }
    @GetMapping
    @Operation(summary = "FeedList",description = "loginUserId는 로그인한 사용자의 PK")
    public ResultResponse<List<FeedGetRes>> getFeedList(@ParameterObject @ModelAttribute FeedGetReq p) {
       log.info("FeedController > getFeedList > p : {}",p);
        List<FeedGetRes> list = service.getFeedList(p);
        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows",list.size()))
                .resultData(list)
                .build();
    }
}
