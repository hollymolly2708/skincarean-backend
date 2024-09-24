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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;

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

    @PatchMapping(path = "/api/admins/current-admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<AdminResponse> updateAdmin(Admin admin,
                                                  @RequestParam(required = false) String address,
                                                  @RequestParam(required = false) String email,
                                                  @RequestParam(required = false) String phone,
                                                  @RequestParam(required = false) String fullName) {

        UpdateAdminRequest updateAdminRequest = new UpdateAdminRequest();
        updateAdminRequest.setAddress(address);
        updateAdminRequest.setPhone(phone);
        updateAdminRequest.setEmail(email);
        updateAdminRequest.setFullName(fullName);

        AdminResponse adminResponse = adminService.updateAdmin(admin, updateAdminRequest);
        return WebResponse.<AdminResponse>builder().data(adminResponse).build();
    }

}
