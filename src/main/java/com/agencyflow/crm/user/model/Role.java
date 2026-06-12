package com.agencyflow.crm.user.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Application role assigned to a user. ADMIN represents platform administration access; SALES_MANAGER owns sales lead work; TEAM_LEAD represents team-level oversight. Current controllers require authentication but do not enforce role-specific endpoint restrictions.",
        example = "SALES_MANAGER"
)
public enum Role {
    ADMIN,
    SALES_MANAGER,
    TEAM_LEAD
}
