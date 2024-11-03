package com.skincareMall.skincareMall.service.admin;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.admin.request.UpdateAdminRequest;
import com.skincareMall.skincareMall.model.admin.response.AdminResponse;
import com.skincareMall.skincareMall.repository.AdminRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private AdminRepository adminRepository;


    public AdminResponse getAdmin(Admin admin) {

        return AdminResponse.builder()
                .phone(admin.getPhone())
                .email(admin.getEmail())
                .fullName(admin.getFullName())
                .address(admin.getAddress())
                .username(admin.getUsernameAdmin())
                .lastUpdatedAt(admin.getLastUpdatedAt())
                .createdAt(admin.getCreatedAt())
                .build();
    }

    @Transactional
    public AdminResponse updateAdmin(Admin admin, UpdateAdminRequest updateAdminRequest) {
        validationService.validate(updateAdminRequest);

        if (Objects.nonNull(updateAdminRequest.getFullName())) {
            admin.setFullName(updateAdminRequest.getFullName());
        }
        if (Objects.nonNull(updateAdminRequest.getAddress())) {
            admin.setAddress(updateAdminRequest.getAddress());
        }
        if (Objects.nonNull(updateAdminRequest.getPhone())) {
            admin.setPhone(updateAdminRequest.getPhone());
        }
        if (Objects.nonNull(updateAdminRequest.getEmail())) {
            admin.setEmail(updateAdminRequest.getEmail());
        }
        admin.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        adminRepository.save(admin);
        return AdminResponse.builder()
                .fullName(admin.getFullName())
                .phone(admin.getPhone())
                .email(admin.getEmail())
                .address(admin.getAddress())
                .username(admin.getUsernameAdmin())
                .createdAt(admin.getCreatedAt())
                .lastUpdatedAt(admin.getLastUpdatedAt())
                .build();
    }


}
