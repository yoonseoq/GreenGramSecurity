package com.green.greengram.common.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey;

}
