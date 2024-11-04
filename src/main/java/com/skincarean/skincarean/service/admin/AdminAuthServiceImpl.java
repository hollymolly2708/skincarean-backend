package com.skincarean.skincarean.service.admin;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.admin.request.LoginAdminRequest;
import com.skincarean.skincarean.model.admin.request.RegisterAdminRequest;
import com.skincarean.skincarean.model.user.response.TokenResponse;
import com.skincarean.skincarean.repository.AdminRepository;
import com.skincarean.skincarean.security.BCrypt;
import com.skincarean.skincarean.utils.Utilities;
import com.skincarean.skincarean.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ValidationService validationService;

    @Transactional
    public void registerAdmin(RegisterAdminRequest registerAdminRequest) {
        validationService.validate(registerAdminRequest);
        if (adminRepository.existsById(registerAdminRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username sudah terdaftar");
        }
        if(!Objects.equals(registerAdminRequest.getPassword(), registerAdminRequest.getConfirmPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password dan confirm password tidak sama");
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
        Admin admin = adminRepository.findById(loginAdminRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah"));

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
