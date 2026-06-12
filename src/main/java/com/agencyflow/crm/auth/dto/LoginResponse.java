package com.agencyflow.crm.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned after successful authentication.")
public record LoginResponse(
        @Schema(description = "JWT access token to use for authenticated requests",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String accessToken
) {
}
