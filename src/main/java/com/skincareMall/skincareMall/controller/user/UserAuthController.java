package com.skincareMall.skincareMall.controller.user;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.user.request.LoginUserRequest;
import com.skincareMall.skincareMall.model.user.request.RegisterUserRequest;
import com.skincareMall.skincareMall.model.user.response.TokenResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.user.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;

    @PostMapping(path = "/api/users/auth/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> registerUser(@RequestParam String username,
                                            @RequestParam String password,
                                            @RequestParam String fullName,
                                            @RequestParam String address,
                                            @RequestParam String phone,
                                            @RequestParam String email,
                                            @RequestParam String confirmPassword
    ) {

        System.out.println(confirmPassword);
        System.out.println(password);

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setAddress(address);
        registerUserRequest.setEmail(email);
        registerUserRequest.setPhone(phone);
        registerUserRequest.setFullName(fullName);
        registerUserRequest.setPassword(password);
        registerUserRequest.setConfirmPassword(confirmPassword);
        registerUserRequest.setUsername(username);

        userAuthService.registerUser(registerUserRequest);
        return WebResponse.<String>builder().data("User berhasil ditambahkan").build();
    }

    @PostMapping(path = "/api/users/auth/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> loginUser(@RequestParam String username, @RequestParam String password) {
        LoginUserRequest loginUserRequest = LoginUserRequest.builder().password(password).username(username).build();
        TokenResponse loginResponse = userAuthService.login(loginUserRequest);
        return WebResponse.<TokenResponse>builder().data(loginResponse).build();
    }

    @DeleteMapping(path = "/api/users/auth/logout",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logoutUser(User user){
        userAuthService.logoutUser(user);
        return WebResponse.<String>builder().data("Logout Success").build();
    }
}
