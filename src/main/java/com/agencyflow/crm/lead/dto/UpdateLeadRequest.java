package com.agencyflow.crm.lead.dto;

public record UpdateLeadRequest(

        String companyName,
        String contactName,
        String email,
        String phone
) {
}