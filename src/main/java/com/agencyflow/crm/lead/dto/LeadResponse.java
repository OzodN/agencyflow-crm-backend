package com.agencyflow.crm.lead.dto;

import com.agencyflow.crm.lead.model.LeadStatus;

import java.time.LocalDateTime;

public record LeadResponse(

        Long id,

        String companyName,

        String contactName,

        String email,

        String phone,

        LeadStatus status,

        Long assignedSalesManagerId,

        Long convertedCustomerId,

        LocalDateTime convertedAt
) {
}
