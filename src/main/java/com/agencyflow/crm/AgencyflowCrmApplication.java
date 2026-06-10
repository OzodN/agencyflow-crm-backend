package com.agencyflow.crm;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Bearer authentication for secured endpoints"
)
public class AgencyflowCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgencyflowCrmApplication.class, args);
    }

}
