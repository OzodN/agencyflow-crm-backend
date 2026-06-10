package com.agencyflow.crm.user.dto;

import com.agencyflow.crm.user.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User response payload returned by user endpoints")
public record UserResponse(
        @Schema(description = "Unique identifier of the user", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "First name of the user", example = "Alice", requiredMode = Schema.RequiredMode.REQUIRED)
        String firstName,

        @Schema(description = "Last name of the user", example = "Johnson", requiredMode = Schema.RequiredMode.REQUIRED)
        String lastName,

        @Schema(description = "Email address of the user", example = "alice@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @Schema(description = "User role in the CRM system", example = "SALES_MANAGER", requiredMode = Schema.RequiredMode.REQUIRED)
        Role role,

        @Schema(description = "Whether the user account is active", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean active
) {
}
