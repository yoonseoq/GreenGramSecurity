package com.green.greengramver3.user;

import com.green.greengramver3.common.model.ResultResponse;
import com.green.greengramver3.user.model.UserSignInReq;
import com.green.greengramver3.user.model.UserSignInRes;
import com.green.greengramver3.user.model.UserSignUpReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("user")
@Tag(name = "유저")
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    public ResultResponse<Integer> signUp(@RequestPart UserSignUpReq p,
                                          @RequestPart(required = false) MultipartFile pic) {
        int result = service.postSignUp(p, pic );
        return ResultResponse.<Integer>builder().resultMessage("회원가입 완료").resultData(result).build();
    }
    @PostMapping("sign-in")
    public ResultResponse<UserSignInRes> signIn(@RequestBody UserSignInReq p) {
        UserSignInRes res = service.selUserList(p);
        return ResultResponse.<UserSignInRes>builder().resultMessage(res.getMessage()).resultData(res).build();
            }
    }

