package com.skincareMall.skincareMall.service.admin;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.admin.request.LoginAdminRequest;
import com.skincareMall.skincareMall.model.admin.request.RegisterAdminRequest;
import com.skincareMall.skincareMall.model.user.response.TokenResponse;

public interface AdminAuthService {

    void registerAdmin(RegisterAdminRequest registerAdminRequest);

    TokenResponse loginAdmin(LoginAdminRequest loginAdminRequest);

    void logoutAdmin(Admin admin);
}
