package com.green.greengram.user;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public ResultResponse<UserSignInRes> signIn(@Valid @RequestBody UserSignInReq p, HttpServletResponse response) {
        UserSignInRes res = service.selUserList(p, response);
        return ResultResponse.<UserSignInRes>builder()
                .resultMessage(res.getMessage())
                .resultData(res)
                .build();
    }

    @GetMapping
    @Operation(summary = "유저 프로필 정보")
    public ResultResponse<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p) {
        UserInfoGetRes res =  service.GetUserInfo(p);
        return ResultResponse.<UserInfoGetRes>builder().resultMessage("유저프로필").resultData(res).build();
    }

    @GetMapping("access-token") // 똑같은 메소드에 똑같은 주소값은 안됨
    @Operation(summary = "accessToken 재발행")
    public ResultResponse<String> getAccessToken(HttpServletRequest req) {
        // 여기 적어 넣으면 스프링이 실제로 넣어줌
        /*
        servlet은 뭐하는 놈인가  req가 들어오면 서블릿이 온다. 이 친구기
        컨트롤러 앞단에 있음. 누굴 호출해줄까?



        아까 오더 포스트 안된 이유. 계속 orderId가 not found여서
         */
        String accessToken = service.getAccessToken(req); //요청받아서 accesstoken에 주소값 보내줌
        return ResultResponse.<String>builder()
                .resultMessage("AccessToken 재발행")
                .resultData(accessToken)
                .build();
    }

    @PatchMapping("pic")
    public ResultResponse<String> patchProfilePic(@ModelAttribute UserPicPatchReq p){
        String pic = service.patchUserPic(p);
        return ResultResponse.<String>builder().resultMessage("프사수정완료")
                .resultData(pic)
                .build();
    }

}



