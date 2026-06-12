package com.agencyflow.crm.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update customer contact details. Customers are created only through lead conversion; this request changes existing customer fields only.")
public record UpdateCustomerRequest(

        @Schema(description = "Updated company or organization name. When omitted or null, the existing company name is retained.", example = "Acme Corporation", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String companyName,

        @Schema(description = "Updated primary customer contact. When omitted or null, the existing contact name is retained.", example = "Jordan Matthews", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String contactName,

        @Schema(description = "Updated customer email address. The current implementation stores the value as supplied and does not enforce email-format validation.", example = "jordan.matthews@acme.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String email,

        @Schema(description = "Updated customer phone number. The current implementation stores the value as supplied.", example = "+1-415-555-0134", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        String phone
) {
}
