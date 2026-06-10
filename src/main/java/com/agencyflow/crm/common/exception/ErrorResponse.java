package com.agencyflow.crm.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Standard error response returned by the API when requests fail")
public record ErrorResponse(
        @Schema(description = "Machine-readable error code", example = "ENTITY_NOT_FOUND", requiredMode = Schema.RequiredMode.REQUIRED)
        ErrorCode code,

        @Schema(description = "Human-readable error message", example = "User not found", requiredMode = Schema.RequiredMode.REQUIRED)
        String message,

        @Schema(description = "Timestamp when the error occurred", example = "2026-06-10T18:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime timestamp,

        @Schema(description = "Request path that triggered the error", example = "/api/v1/users/1", requiredMode = Schema.RequiredMode.REQUIRED)
        String path
) {
}
