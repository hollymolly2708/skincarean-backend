package com.skincarean.skincarean.controller.admin;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.admin.request.UpdateAdminRequest;
import com.skincarean.skincarean.model.admin.response.AdminResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.repository.AdminRepository;
import com.skincarean.skincarean.service.admin.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @GetMapping(path = "/api/admins/current-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<AdminResponse> getAdmin(Admin admin) {
        AdminResponse adminResponse = adminServiceImpl.getAdmin(admin);
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

        AdminResponse adminResponse = adminServiceImpl.updateAdmin(admin, updateAdminRequest);
        return WebResponse.<AdminResponse>builder().data(adminResponse).build();
    }

}
