package com.skincareMall.skincareMall.controller.admin;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.admin.request.LoginAdminRequest;
import com.skincareMall.skincareMall.model.admin.request.RegisterAdminRequest;
import com.skincareMall.skincareMall.model.user.response.TokenResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.admin.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminAuthController {
    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping(path = "/api/admins/auth/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestParam String fullName,
                                        @RequestParam String password,
                                        @RequestParam String username,
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
        registerAdminRequest.setPassword(password);
        registerAdminRequest.setFullName(fullName);

        adminAuthService.registerAdmin(registerAdminRequest);
        return WebResponse.<String>builder().data("Admin berhasil ditambahkan").build();
    }

    @PostMapping(path = "/api/admins/auth/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> loginAdmin(LoginAdminRequest loginAdminRequest) {
        TokenResponse tokenResponse = adminAuthService.loginAdmin(loginAdminRequest);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping(path = "/api/admins/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logoutAdmin(Admin admin) {
        adminAuthService.logoutAdmin(admin);
        return WebResponse.<String>builder().data("Logout berhasil").build();
    }
}
