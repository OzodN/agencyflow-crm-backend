package com.agencyflow.crm.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer details returned by customer endpoints. Customer records originate from qualified lead conversion.")
public record CustomerResponse(

        @Schema(description = "Server-generated unique identifier of the customer.", example = "501", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Company or organization represented by the customer.", example = "Acme Corporation", requiredMode = Schema.RequiredMode.REQUIRED)
        String companyName,

        @Schema(description = "Primary contact person for the customer.", example = "Jordan Matthews", requiredMode = Schema.RequiredMode.REQUIRED)
        String contactName,

        @Schema(description = "Email address stored for the customer contact, when provided.", example = "jordan.matthews@acme.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String email,

        @Schema(description = "Phone number stored for the customer contact, when provided.", example = "+1-415-555-0134", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String phone
) {
}
