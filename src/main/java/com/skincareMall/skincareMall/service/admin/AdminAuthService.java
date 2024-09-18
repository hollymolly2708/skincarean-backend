package com.skincareMall.skincareMall.service.admin;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.admin.request.LoginAdminRequest;
import com.skincareMall.skincareMall.model.admin.request.RegisterAdminRequest;
import com.skincareMall.skincareMall.model.admin.response.AdminResponse;
import com.skincareMall.skincareMall.model.user.response.TokenResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.repository.AdminRepository;
import com.skincareMall.skincareMall.security.BCrypt;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
public class AdminAuthService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ValidationService validationService;

    @Transactional
    public void registerAdmin(RegisterAdminRequest registerAdminRequest) {
        validationService.validate(registerAdminRequest);
        if (adminRepository.existsById(registerAdminRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        Admin admin = new Admin();
        admin.setAddress(registerAdminRequest.getAddress());
        admin.setEmail(registerAdminRequest.getEmail());
        admin.setFullName(registerAdminRequest.getFullName());
        admin.setAddress(registerAdminRequest.getAddress());
        admin.setUsernameAdmin(registerAdminRequest.getUsername());
        admin.setIsAdmin(registerAdminRequest.getIsAdmin());
        admin.setPhone(registerAdminRequest.getPhone());
        admin.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        admin.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        admin.setPasswordAdmin(BCrypt.hashpw(registerAdminRequest.getPassword(), BCrypt.gensalt()));
        adminRepository.save(admin);
    }

    @Transactional
    public TokenResponse loginAdmin(LoginAdminRequest loginAdminRequest) {
        validationService.validate(loginAdminRequest);
        Admin admin = adminRepository.findById(loginAdminRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username or password is wrong"));
        if (!Objects.equals(loginAdminRequest.getPassword(), loginAdminRequest.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password is wrong");
        }

        if (BCrypt.checkpw(loginAdminRequest.getPassword(), admin.getPasswordAdmin())) {
            admin.setToken(UUID.randomUUID().toString());
            admin.setTokenCreatedAt(System.currentTimeMillis());
            admin.setTokenExpiredAt(Utilities.next30days());
        }
        return TokenResponse.builder()
                .tokenExpiredAt(admin.getTokenExpiredAt())
                .token(admin.getToken())
                .tokenCreatedAt(admin.getTokenCreatedAt())
                .build();

    }
    @Transactional
    public void logoutAdmin (Admin admin){
        admin.setTokenExpiredAt(null);
        admin.setTokenCreatedAt(null);
        admin.setToken(null);
        adminRepository.save(admin);
    }

}
