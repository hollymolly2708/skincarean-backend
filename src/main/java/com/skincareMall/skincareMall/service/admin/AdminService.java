package com.skincareMall.skincareMall.service.admin;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.admin.request.UpdateAdminRequest;
import com.skincareMall.skincareMall.model.admin.response.AdminResponse;

public interface AdminService {
    AdminResponse getAdmin(Admin admin);
    AdminResponse updateAdmin(Admin admin, UpdateAdminRequest updateAdminRequest);
}
