package com.green.greengramver2.user;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.user.model.UserSignInReq;
import com.green.greengramver2.user.model.UserSignInRes;
import com.green.greengramver2.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;

    public int postSignUp( UserSignUpReq p, MultipartFile pic) {
        String savedPicName = (pic != null? myFileUtils.makeRandomFileName(pic):null);
        // 비밀번호 암호화
        String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
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
        try {
            myFileUtils.transferTo((MultipartFile) pic,filePath);
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
    public UserSignInRes selUserList(UserSignInReq p) {


        UserSignInRes res = mapper.selUserByUid(p.getUid());

        if (res == null) {
            res = new UserSignInRes(); // 메세지 보낼려고 객체 생성
            res.setMessage("아이디 확인 바람");
            return res;
        }

        if ( !BCrypt.checkpw(p.getUpw(), res.getUpw())) {
            res = new UserSignInRes();
            res.setMessage("비번 확인 요망!");
            return res;
        }

        res.setMessage("로그인 성공!!");
        return res;
    }




}
