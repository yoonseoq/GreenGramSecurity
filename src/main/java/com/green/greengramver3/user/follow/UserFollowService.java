package com.green.greengramver3.user.follow;

import com.green.greengramver3.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFollowService {
    private final UserFollowMapper userFollowMapper;

    public int postUserFollow(UserFollowReq p){

        return userFollowMapper.insUserFollow(p);
    }
    public int delUserFollow(UserFollowReq p){
        return userFollowMapper.delUserFollow(p);
    }
}
