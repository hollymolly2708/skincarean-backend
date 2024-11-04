package com.skincarean.skincarean.service.admin;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.admin.request.LoginAdminRequest;
import com.skincarean.skincarean.model.admin.request.RegisterAdminRequest;
import com.skincarean.skincarean.model.user.response.TokenResponse;

public interface AdminAuthService {

    void registerAdmin(RegisterAdminRequest registerAdminRequest);

    TokenResponse loginAdmin(LoginAdminRequest loginAdminRequest);

    void logoutAdmin(Admin admin);
}
