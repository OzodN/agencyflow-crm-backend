package com.agencyflow.crm.user.dto;

import com.agencyflow.crm.user.model.Role;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role,
        boolean active
) {
}
