package com.agencyflow.crm.common.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        ErrorCode code,
        String message,
        LocalDateTime timestamp,
        String path
) {
}
