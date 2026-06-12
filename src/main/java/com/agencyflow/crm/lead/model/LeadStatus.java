package com.agencyflow.crm.lead.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Lead workflow state. NEW leads may move to CONTACTED or REJECTED; CONTACTED leads may move to QUALIFIED or REJECTED; QUALIFIED leads may be converted to a customer; CONVERTED and REJECTED are terminal states in the current implementation.",
        example = "QUALIFIED"
)
public enum LeadStatus {

    NEW,
    CONTACTED,
    QUALIFIED,
    CONVERTED,
    REJECTED
}
