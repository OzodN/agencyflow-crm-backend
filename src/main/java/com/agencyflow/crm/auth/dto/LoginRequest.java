package com.agencyflow.crm.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login request payload containing user credentials for JWT token issuance.")
public record LoginRequest(

        @Schema(description = "Email address of the user. Required, must be non-blank, and must use a valid email format.",
                example = "alice.johnson@agencyflow.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Email
        @NotBlank
        String email,

        @Schema(description = "User password. Required and must be non-blank; the current API does not expose password complexity rules.",
                example = "CorrectHorseBattery7!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String password
) {
}
