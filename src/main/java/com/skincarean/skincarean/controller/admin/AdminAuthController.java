package com.skincarean.skincarean.controller.admin;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.admin.request.LoginAdminRequest;
import com.skincarean.skincarean.model.admin.request.RegisterAdminRequest;
import com.skincarean.skincarean.model.user.response.TokenResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.service.admin.AdminAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminAuthController {
    @Autowired
    private AdminAuthServiceImpl adminAuthServiceImpl;

    @PostMapping(path = "/api/admins/auth/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestParam String fullName,
                                        @RequestParam String password,
                                        @RequestParam String username,
                                        @RequestParam String confirmPassword,
                                        @RequestParam String address,
                                        @RequestParam String email,
                                        @RequestParam String phone,
                                        @RequestParam Boolean isAdmin
    ) {
        RegisterAdminRequest registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setIsAdmin(isAdmin);
        registerAdminRequest.setPhone(phone);
        registerAdminRequest.setAddress(address);
        registerAdminRequest.setEmail(email);
        registerAdminRequest.setUsername(username);
        registerAdminRequest.setConfirmPassword(confirmPassword);
        registerAdminRequest.setPassword(password);
        registerAdminRequest.setFullName(fullName);

        adminAuthServiceImpl.registerAdmin(registerAdminRequest);
        return WebResponse.<String>builder().data("Admin berhasil ditambahkan").build();
    }

    @PostMapping(path = "/api/admins/auth/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> loginAdmin(LoginAdminRequest loginAdminRequest) {
        TokenResponse tokenResponse = adminAuthServiceImpl.loginAdmin(loginAdminRequest);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping(path = "/api/admins/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logoutAdmin(Admin admin) {
        adminAuthServiceImpl.logoutAdmin(admin);
        return WebResponse.<String>builder().data("Logout berhasil").build();
    }
}
