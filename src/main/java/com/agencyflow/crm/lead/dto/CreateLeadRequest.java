package com.agencyflow.crm.lead.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new sales lead. Leads start in NEW status and must be assigned to an existing user with the SALES_MANAGER role.")
public record CreateLeadRequest(

        @Schema(description = "Company or organization represented by the lead. Required and must contain non-whitespace text.", example = "Acme Corporation", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        String companyName,

        @Schema(description = "Primary contact person for the lead. Required and must contain non-whitespace text.", example = "Jordan Matthews", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        String contactName,

        @Schema(description = "Optional email address for the lead contact. The current implementation stores the value as supplied and does not enforce email-format validation.", example = "jordan.matthews@acme.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String email,

        @Schema(description = "Optional phone number for the lead contact. The current implementation stores the value as supplied.", example = "+1-415-555-0134", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String phone,

        @Schema(description = "Identifier of the existing user who will own the lead. Required by business logic and the referenced user must have the SALES_MANAGER role.", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        Long assignedSalesManagerId
) {
}
