package com.green.greengramver3.user;

import com.green.greengramver3.common.model.ResultResponse;
import com.green.greengramver3.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService service;
    @PostMapping("sign-up")
    public ResultResponse<Integer> signUp(@RequestPart UserSignUpReq p,
                                          @RequestPart(required = false) MultipartFile pic) {
        int result = service.postSignUp(p, pic);
        return ResultResponse.<Integer>builder().resultMessage("회원가입 완료").resultData(result).build();
    }

    @PostMapping("sign-in")
    public ResultResponse<UserSignInRes> signIn(@RequestBody UserSignInReq p) {
        UserSignInRes res = service.selUserList(p);
        return ResultResponse.<UserSignInRes>builder().resultMessage(res.getMessage()).resultData(res).build();
    }

    @GetMapping
    @Operation(summary = "유저 프로필 정보")
    public ResultResponse<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p) {
        UserInfoGetRes res =  service.GetUserInfo(p);
        return ResultResponse.<UserInfoGetRes>builder().resultMessage("유저프로필").resultData(res).build();
    }

    @PatchMapping("pic")
    public ResultResponse<String> patchProfilePic(@ModelAttribute UserPicPatchReq p){
        String pic = service.patchUserPic(p);
        return ResultResponse.<String>builder().resultMessage("프사수정완료")
                .resultData(pic)
                .build();
    }

}



