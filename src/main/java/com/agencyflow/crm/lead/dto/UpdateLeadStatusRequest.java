package com.agencyflow.crm.lead.dto;

import com.agencyflow.crm.lead.model.LeadStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to move a lead to the next workflow status. The requested transition must be allowed by the current lead status.")
public record UpdateLeadStatusRequest(

        @Schema(description = "Target lead status. Valid transitions are NEW to CONTACTED or REJECTED, CONTACTED to QUALIFIED or REJECTED, and QUALIFIED to CONVERTED. CONVERTED and REJECTED are terminal.", example = "QUALIFIED", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        LeadStatus status
) {
}
