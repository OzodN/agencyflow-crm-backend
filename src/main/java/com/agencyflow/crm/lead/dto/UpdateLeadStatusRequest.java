package com.agencyflow.crm.lead.dto;

import com.agencyflow.crm.lead.model.LeadStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateLeadStatusRequest(
        @NotBlank
        @NotNull
        LeadStatus status
) {
}
