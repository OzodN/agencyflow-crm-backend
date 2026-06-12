package com.agencyflow.crm.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Standard error response returned by the API when a request cannot be completed.")
public record ErrorResponse(
        @Schema(description = "Machine-readable category for the error.", example = "ENTITY_NOT_FOUND", requiredMode = Schema.RequiredMode.REQUIRED)
        ErrorCode code,

        @Schema(description = "Human-readable message explaining the failure.", example = "Lead not found with id: 101", requiredMode = Schema.RequiredMode.REQUIRED)
        String message,

        @Schema(description = "Server timestamp when the error response was created.", example = "2026-06-12T10:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime timestamp,

        @Schema(description = "Request path that triggered the error.", example = "/api/v1/leads/101", requiredMode = Schema.RequiredMode.REQUIRED)
        String path
) {
}
