package com.green.greengramver3.user;

import com.green.greengramver3.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insUser(UserSignUpReq p);
    UserSignInRes selUserByUid(String uid);
    UserInfoGetRes selUserInfo(UserInfoGetReq p);
    String patchUserPic(UserPicPatchReq p);

}
