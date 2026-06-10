package com.agencyflow.crm.lead.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateLeadRequest(

        @NotBlank
        String companyName,

        @NotBlank
        String contactName,

        String email,

        String phone,

        Long assignedSalesManagerId
) {
}