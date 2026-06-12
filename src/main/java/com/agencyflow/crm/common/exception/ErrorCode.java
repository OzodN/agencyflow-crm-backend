package com.agencyflow.crm.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Machine-readable API error categories. ENTITY_NOT_FOUND identifies missing records; VALIDATION_ERROR identifies malformed or constraint-violating requests; BUSINESS_ERROR identifies workflow rule violations; ACCESS_DENIED identifies authentication or authorization failures; INTERNAL_ERROR identifies unexpected server failures.",
        example = "VALIDATION_ERROR"
)
public enum ErrorCode {

    ENTITY_NOT_FOUND,
    VALIDATION_ERROR,
    BUSINESS_ERROR,
    ACCESS_DENIED,
    INTERNAL_ERROR
}
