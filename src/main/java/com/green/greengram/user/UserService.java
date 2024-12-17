package com.green.greengram.user;

import com.green.greengram.common.MyFileUtils;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

            // 멀티파트파일에 있는 데이터를 저기로 넘겨줌 그러면서 에러도 거기서 던짐
        try {
            myFileUtils.transferTo(pic,filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public UserInfoGetRes GetUserInfo(UserInfoGetReq p) {
        return mapper.selUserInfo(p);
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
