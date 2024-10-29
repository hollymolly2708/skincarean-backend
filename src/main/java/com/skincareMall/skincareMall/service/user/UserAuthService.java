package com.skincareMall.skincareMall.service.user;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.google_auth.request.GoogleLoginTokenRequest;
import com.skincareMall.skincareMall.model.user.request.LoginUserRequest;
import com.skincareMall.skincareMall.model.user.request.RegisterUserRequest;
import com.skincareMall.skincareMall.model.user.response.TokenResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;

public interface UserAuthService {
    WebResponse<String> registerUser(RegisterUserRequest request);

    WebResponse<TokenResponse> login(LoginUserRequest loginUserRequest);

    WebResponse<String> logoutUser(User user);

    WebResponse<TokenResponse> verifyToken(GoogleLoginTokenRequest googleLoginTokenRequest);
}
