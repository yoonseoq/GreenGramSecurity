package com.green.greengram.common.config.security;

import com.green.greengram.common.config.jwt.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public JwtUser getSignedUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                                                                        .getAuthentication()
                                                                        .getPrincipal();
        return myUserDetails.getJwtUser();
    }

    public long getSignedUserId() {
        JwtUser jwtUser = getSignedUser();
        if (jwtUser == null) {
            return 0;
        }
        return jwtUser.getSignedUserId();
    }
}
