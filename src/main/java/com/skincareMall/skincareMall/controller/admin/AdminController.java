package com.skincareMall.skincareMall.controller.admin;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.admin.request.UpdateAdminRequest;
import com.skincareMall.skincareMall.model.admin.response.AdminResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.repository.AdminRepository;
import com.skincareMall.skincareMall.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/api/admins/current-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<AdminResponse> getAdmin(Admin admin) {
        AdminResponse adminResponse = adminService.getAdmin(admin);
        return WebResponse.<AdminResponse>builder().data(adminResponse).build();
    }
    @PatchMapping(path = "/api/admins/current-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<AdminResponse> updateAdmin(Admin admin, UpdateAdminRequest updateAdminRequest){
        AdminResponse adminResponse = adminService.updateAdmin(admin, updateAdminRequest);
        return WebResponse.<AdminResponse>builder().data(adminResponse).build();
    }

}
