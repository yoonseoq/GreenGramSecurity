package com.green.greengramver3.common.config.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class JwtUser {
    private long signedUserId;
    private List<String> roles;
}
