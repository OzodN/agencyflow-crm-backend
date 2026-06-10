package com.agencyflow.crm.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login request payload containing user credentials")
public record LoginRequest(

        @Schema(description = "Email address of the user",
                example = "alice@example.com",
                minLength = 13,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Email
        @NotBlank
        String email,

        @Schema(description = "User password",
                example = "P@ssw0rd!",
                minLength = 8,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String password
) {
}
