package com.agencyflow.crm.auth.service;

import com.agencyflow.crm.auth.dto.LoginRequest;
import com.agencyflow.crm.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
