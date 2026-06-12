package com.agencyflow.crm.lead.dto;

import com.agencyflow.crm.lead.model.LeadStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Lead details returned by lead endpoints, including assignment and conversion state.")
public record LeadResponse(

        @Schema(description = "Server-generated unique identifier of the lead.", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Company or organization represented by the lead.", example = "Acme Corporation", requiredMode = Schema.RequiredMode.REQUIRED)
        String companyName,

        @Schema(description = "Primary contact person for the lead.", example = "Jordan Matthews", requiredMode = Schema.RequiredMode.REQUIRED)
        String contactName,

        @Schema(description = "Email address stored for the lead contact, when provided.", example = "jordan.matthews@acme.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String email,

        @Schema(description = "Phone number stored for the lead contact, when provided.", example = "+1-415-555-0134", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String phone,

        @Schema(description = "Current workflow status of the lead.", example = "QUALIFIED", requiredMode = Schema.RequiredMode.REQUIRED)
        LeadStatus status,

        @Schema(description = "Identifier of the SALES_MANAGER user assigned to own this lead.", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        Long assignedSalesManagerId,

        @Schema(description = "Identifier of the customer created from this lead. Present only after successful lead conversion.", example = "501", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        Long convertedCustomerId,

        @Schema(description = "Server timestamp when the lead was converted to a customer. Present only after successful lead conversion.", example = "2026-06-12T10:30:00", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        LocalDateTime convertedAt
) {
}
