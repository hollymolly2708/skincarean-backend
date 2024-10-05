package com.skincareMall.skincareMall.service.user;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.user.request.LoginUserRequest;
import com.skincareMall.skincareMall.model.user.request.RegisterUserRequest;
import com.skincareMall.skincareMall.model.user.response.TokenResponse;
import com.skincareMall.skincareMall.repository.UserRepository;
import com.skincareMall.skincareMall.security.BCrypt;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.aspectj.weaver.patterns.IToken;
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
    public void registerUser(RegisterUserRequest request) {
        validationService.validate(request);
        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username sudah terdaftar");
        }
        if (!Objects.equals(request.getConfirmPassword(), request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password dan konfirmasi password tidak sama");
        }

        System.out.println(request.getConfirmPassword());
        System.out.println(request.getPassword());

        User user = new User();
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPasswordUser(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setUsernameUser(request.getUsername());
        user.setCreatedAt(changeFormatToTimeStamp());
        user.setLastUpdatedAt(changeFormatToTimeStamp());
        user.setAddress(request.getAddress());
        userRepository.save(user);
    }

    @Transactional
    public TokenResponse login(LoginUserRequest loginUserRequest) {
        validationService.validate(loginUserRequest);
        User user = userRepository.findById(loginUserRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username atau password salah"));
        if (BCrypt.checkpw(loginUserRequest.getPassword(), user.getPasswordUser())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30days());
            user.setTokenCreatedAt(System.currentTimeMillis());
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .tokenCreatedAt(user.getTokenCreatedAt())
                    .tokenExpiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username atau password salah");
        }
    }

    @Transactional
    public void logoutUser(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);
        user.setTokenCreatedAt(null);
        userRepository.save(user);

    }


}

