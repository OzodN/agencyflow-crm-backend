package com.agencyflow.crm.user.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Roles assigned to application users")
public enum Role {
    ADMIN,
    SALES_MANAGER,
    TEAM_LEAD
}