package com.green.greengram.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "YoonStagram",
                version = "v1",
                description = "윤스타그램"
        )
        ,security = @SecurityRequirement(name = "Authorization")
)

// 스웨거에서 인증처리 할수 있게끔 처리함
@SecurityScheme(
        type = SecuritySchemeType.HTTP
        , name = "Authorization"
        , in = SecuritySchemeIn.HEADER
        , bearerFormat = "JWT"
        , scheme = "Bearer"
)

public class SwaggerConfiguration {
}
