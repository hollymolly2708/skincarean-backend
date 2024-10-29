package com.skincareMall.skincareMall.controller.user;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.google_auth.request.GoogleLoginTokenRequest;
import com.skincareMall.skincareMall.model.user.request.LoginUserRequest;
import com.skincareMall.skincareMall.model.user.request.RegisterUserRequest;
import com.skincareMall.skincareMall.model.user.response.TokenResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.user.UserAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAuthController {
    @Autowired
    private UserAuthServiceImpl userAuthServiceImpl;

    @Autowired
    private UserAuthServiceImpl googleLoginAuthService;

    @PostMapping("/api/users/auth/login/google/verify")
    public WebResponse<TokenResponse> verifyToken(@RequestBody GoogleLoginTokenRequest tokenRequest) {
        return googleLoginAuthService.verifyToken(tokenRequest);
    }

    @PostMapping(path = "/api/users/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> registerUser(@RequestBody RegisterUserRequest registerUserRequest
    ) {


        RegisterUserRequest.builder()
                .address(registerUserRequest.getAddress())
                .email(registerUserRequest.getEmail())
                .phone(registerUserRequest.getPhone())
                .fullName(registerUserRequest.getFullName())
                .photoProfile(registerUserRequest.getPhotoProfile())
                .password(registerUserRequest.getPassword())
                .confirmPassword(registerUserRequest.getConfirmPassword())
                .username(registerUserRequest.getUsername())

                .build();


        return userAuthServiceImpl.registerUser(registerUserRequest);

    }

    @PostMapping(path = "/api/users/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest) {

        LoginUserRequest.builder().password(loginUserRequest.getPassword()).username(loginUserRequest.getUsername()).build();
        return userAuthServiceImpl.login(loginUserRequest);

    }

    @DeleteMapping(path = "/api/users/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logoutUser(User user) {
        return userAuthServiceImpl.logoutUser(user);

    }


}
