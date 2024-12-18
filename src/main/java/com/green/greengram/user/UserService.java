package com.green.greengram.user;

import com.green.greengram.common.CookieUtils;
import com.green.greengram.common.MyFileUtils;
import com.green.greengram.common.config.jwt.JwtUser;
import com.green.greengram.common.config.jwt.TokenProvider;
import com.green.greengram.user.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CookieUtils cookieUtils;


    public int postSignUp( UserSignUpReq p, MultipartFile pic) {
        String savedPicName = (pic != null? myFileUtils.makeRandomFileName(pic):null);
        // 비밀번호 암호화
        //String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        String hashedPassword = passwordEncoder.encode(p.getUpw());
        log.info("hashedPassword: {} ", hashedPassword);
        p.setUpw(hashedPassword);
        p.setPic(savedPicName);

        int result = mapper.insUser(p);

        if (pic == null) {

            return result;
        }

        long userId = p.getUserId();
        String middlePath = String.format("user/%d", userId);
        myFileUtils.makeFolders(middlePath);
        String filePath = String.format("%s/%s", middlePath,savedPicName);

            // 멀티파트파일에 있는 데이터를 저기로 넘겨줌 그러면서 에러도 거기서 던짐
        try {
            myFileUtils.transferTo(pic,filePath);
        } catch (IOException e) {
           e.printStackTrace();
        }
        return result;
    }

    public UserSignInRes selUserList(UserSignInReq p, HttpServletResponse response) {


        UserSignInRes res = mapper.selUserByUid(p.getUid());

        if (res == null) {
            res = new UserSignInRes(); // 메세지 보낼려고 객체 생성
            res.setMessage("아이디 확인 바람");
            return res;
        // else if ( !BCrypt.checkpw(p.getUpw(), res.getUpw())) { //비밀번호가 다를시
        } else if ( !passwordEncoder.matches(p.getUpw(), res.getUpw())) {

            res = new UserSignInRes();
            res.setMessage("비번 확인 요망!");
            return res;

        }
        /*
         한별씨 이제 뭐해야하죠? -> 잘 모르겠습니다. 로그인시켜줘야죠
         인증처리 jwt 해줘야 한다
         은아: jwt 토큰생성 1개? 2개? 왜 두개죠???
            accessToken, refreshToken 두개만들어야함
         */

        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(res.getUserId());
        jwtUser.setRoles(new ArrayList<>(2));

        jwtUser.getRoles().add("ROLE_USER");
        jwtUser.getRoles().add("ROLE_ADMIN");

        String accessToken = tokenProvider.generateToken(jwtUser, Duration.ofMinutes(20));;
        String refreshToken = tokenProvider.generateToken(jwtUser, Duration.ofDays(15));

        int maxAge = 1_296_000;
        cookieUtils.setCookie(response,"refreshToken",refreshToken,maxAge);


        res.setMessage("로그인 성공!!");
        res.setAccessToken(accessToken);
        return res;
    }

    public UserInfoGetRes GetUserInfo(UserInfoGetReq p) {
        return mapper.selUserInfo(p);
    }

    public String getAccessToken(HttpServletRequest req) {
        Cookie cookie = cookieUtils.getCookie(req,"refreshToken");
        String refreshToken = cookie.getValue();
        return refreshToken;
    }



    public String patchUserPic(UserPicPatchReq p) {
       /*
        이미지 파일 처리해야해서 myFileUtils 를 사용해서
        해당파일명에서 파일을 가져오고 지우고 새로운사진을 가져옴
        transferTo

       랜덤한 파일명 만들고

        */
        //1.저장할 파일명 생성
        String savedPicName = (p.getPic() != null? myFileUtils.makeRandomFileName(p.getPic()):null);

        /*
        2.기존파일삭제 (방법 2가지)
        [1]폴더를 지운다 << 그나마나음
        [2] select 해서 기존파일명을 얻어온다
        [3] 기존파일명을 프론트에서 받는다
         */
        String deletePath = String.format("%s/user/%d",myFileUtils.getUploadPath(),p.getSignedUserId());
        myFileUtils.deleteFolder(deletePath,false);

        //3.원하는 위치에 저장할 파일명으로 이동
        if(p.getPic() != null) {
            return null;
        }

        String filePath = String.format("user/%d/%s",p.getSignedUserId(),savedPicName);
        try {
            myFileUtils.transferTo(p.getPic(),filePath);
        }catch (IOException e){
            e.printStackTrace();
        }
        //4. db에 튜플 수정(update)



        return savedPicName;
    }





}
