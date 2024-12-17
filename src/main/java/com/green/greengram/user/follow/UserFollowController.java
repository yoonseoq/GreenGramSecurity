package com.green.greengram.user.follow;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/follow")
public class UserFollowController {
    private final UserFollowService userFollowService;
    public ResultResponse<Integer> postUserFollow(@RequestBody UserFollowReq p){
        int result = userFollowService.postUserFollow(p);
        return ResultResponse.<Integer>builder()
                .resultMessage("팔로우")
                .resultData(result).build();
    }
    public ResultResponse<Integer> delUserFollow(@ModelAttribute UserFollowReq p){
        int result = userFollowService.delUserFollow(p);
        return ResultResponse.<Integer>builder()
                .resultMessage("언팔로우")
                .resultData(result).build();
    }
}
