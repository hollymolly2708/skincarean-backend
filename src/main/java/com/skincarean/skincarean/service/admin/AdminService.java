package com.skincarean.skincarean.service.admin;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.admin.request.UpdateAdminRequest;
import com.skincarean.skincarean.model.admin.response.AdminResponse;

public interface AdminService {
    AdminResponse getAdmin(Admin admin);
    AdminResponse updateAdmin(Admin admin, UpdateAdminRequest updateAdminRequest);
}
