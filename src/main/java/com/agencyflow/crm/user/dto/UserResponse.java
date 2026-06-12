package com.agencyflow.crm.user.dto;

import com.agencyflow.crm.user.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User account details returned by user endpoints.")
public record UserResponse(
        @Schema(description = "Server-generated unique identifier of the user.", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "User's first name.", example = "Alice", requiredMode = Schema.RequiredMode.REQUIRED)
        String firstName,

        @Schema(description = "User's last name.", example = "Johnson", requiredMode = Schema.RequiredMode.REQUIRED)
        String lastName,

        @Schema(description = "Email address used by the user to sign in.", example = "alice.johnson@agencyflow.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @Schema(description = "User role in the CRM system.", example = "SALES_MANAGER", requiredMode = Schema.RequiredMode.REQUIRED)
        Role role,

        @Schema(description = "Whether the user account is active.", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean active
) {
}
