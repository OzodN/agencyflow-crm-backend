package com.agencyflow.crm.user.service;

import com.agencyflow.crm.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getById(Long id);

    List<UserResponse> getAll();
}
