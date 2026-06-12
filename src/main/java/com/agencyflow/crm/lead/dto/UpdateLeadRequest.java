package com.agencyflow.crm.lead.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update editable lead contact details. All fields are optional; null fields are ignored by the current implementation.")
public record UpdateLeadRequest(

        @Schema(description = "Updated company or organization name. When omitted or null, the existing company name is retained.", example = "Acme Corporation", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String companyName,

        @Schema(description = "Updated primary contact person. When omitted or null, the existing contact name is retained.", example = "Jordan Matthews", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String contactName,

        @Schema(description = "Updated contact email address. The current implementation stores the value as supplied and does not enforce email-format validation.", example = "jordan.matthews@acme.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String email,

        @Schema(description = "Updated contact phone number. The current implementation stores the value as supplied.", example = "+1-415-555-0134", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String phone
) {
}
