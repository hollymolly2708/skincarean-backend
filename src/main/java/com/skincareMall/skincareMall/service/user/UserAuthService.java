package com.skincareMall.skincareMall.service.user;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.user.request.LoginUserRequest;
import com.skincareMall.skincareMall.model.user.request.RegisterUserRequest;
import com.skincareMall.skincareMall.model.user.response.TokenResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.repository.UserRepository;
import com.skincareMall.skincareMall.security.BCrypt;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

import static com.skincareMall.skincareMall.utils.Utilities.changeFormatToTimeStamp;
import static com.skincareMall.skincareMall.utils.Utilities.next30days;


@Service
public class UserAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;

    @Transactional
    public WebResponse<String> registerUser(RegisterUserRequest request) {
        validationService.validate(request);
        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username sudah terdaftar");
        }
        if (!Objects.equals(request.getConfirmPassword(), request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password dan konfirmasi password tidak sama");
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email sudah terdaftar");
        }


        User user = new User();
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPasswordUser(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setUsernameUser(request.getUsername());
        user.setCreatedAt(changeFormatToTimeStamp());
        user.setLastUpdatedAt(changeFormatToTimeStamp());
        user.setAddress(request.getAddress());

        try {
            userRepository.save(user);
            return WebResponse.<String>builder().isSuccess(true).data("Register berhasil").build();
        } catch (Exception e) {
            return WebResponse.<String>builder().isSuccess(false).errors("Register gagal").build();
        }


    }

    @Transactional
    public WebResponse<TokenResponse> login(LoginUserRequest loginUserRequest) {
        validationService.validate(loginUserRequest);
        User user = userRepository.findById(loginUserRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah"));

        if (BCrypt.checkpw(loginUserRequest.getPassword(), user.getPasswordUser())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30days());
            user.setTokenCreatedAt(System.currentTimeMillis());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah");

        }

        //simpan token kedalam database
        try {
            userRepository.save(user);
            return WebResponse.<TokenResponse>builder().isSuccess(true).data(TokenResponse.builder().token(user.getToken()).tokenCreatedAt(user.getTokenCreatedAt()).tokenExpiredAt(user.getTokenExpiredAt()).build()).build();

        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.<TokenResponse>builder().isSuccess(false).errors("Kesalahan saat memproses login").build();
        }

    }

    @Transactional
    public WebResponse<String> logoutUser(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);
        user.setTokenCreatedAt(null);

        //hapus session dari database
        try {
            userRepository.save(user);
            return WebResponse.<String>builder().isSuccess(true).data("Logout berhasil").build();
        } catch (Exception e) {
            return WebResponse.<String>builder().isSuccess(false).errors("Gagal menghapus session").build();
        }


    }


}

