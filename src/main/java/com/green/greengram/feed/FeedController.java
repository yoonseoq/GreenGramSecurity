package com.green.greengram.feed;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
                                                @Valid @RequestPart FeedPostReq p) {
        log.info("FeedController > getFeedList > p : {}",p); // 이거 로그 일단 무권 짝어야함
        FeedPostRes res = service.postFeed(pics, p);
        List<FeedGetRes> list = new ArrayList<>();
        return ResultResponse.<FeedPostRes>builder()
                .resultMessage("피드등록완료")
                .resultData(res)
                .build();
    }
    @GetMapping
    @Operation(summary = "FeedList",description = "loginUserId는 로그인한 사용자의 PK")
    public ResultResponse<List<FeedGetRes>> getFeedList(@Valid @ParameterObject @ModelAttribute FeedGetReq p) {
       log.info("FeedController > getFeedList > p : {}",p);
        List<FeedGetRes> list = service.getFeedList3(p);
        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows",list.size()))
                .resultData(list)
                .build();
    } // n+1이슈가 발생할수 있음

    // 3번 셀렉트하고 피드 5000개 페이지당 20개씩 가져온다
    /*public List<FeedGetRes> getFeedList2(FeedGetReq p) {

        List<FeedGetRes> list = service.getFeedList(p); //피드 리스트

        List<Long> feedIds = new ArrayList<>();
        //list 에 있는 사이즈만큼  Long 타입의 feedIds 리스트 생성
        for (FeedGetRes item : list) {
            feedIds.add(item.getFeedId());
        }
        log.info("feedIds : {}", feedIds);

    }*/
    @DeleteMapping
    @Operation(summary = "Feed 삭제", description = "피드의 댓글, 좋아요 모두 삭제처리")
    public ResultResponse<Integer> deleteFeed(@ParameterObject @ModelAttribute FeedDeleteReq p) {
        log.info("FeedController > deleteFeed > p : {}",p);
        int insult = service.deleteFeed(p);
        return ResultResponse.<Integer>builder()
                .resultMessage("피드가 삭제됨")
                .resultData(insult).build();
    }
}
