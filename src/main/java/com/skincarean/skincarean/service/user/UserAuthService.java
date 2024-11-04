package com.skincarean.skincarean.service.user;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.google_auth.request.GoogleLoginTokenRequest;
import com.skincarean.skincarean.model.user.request.LoginUserRequest;
import com.skincarean.skincarean.model.user.request.RegisterUserRequest;
import com.skincarean.skincarean.model.user.response.TokenResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;

public interface UserAuthService {
    WebResponse<String> registerUser(RegisterUserRequest request);

    WebResponse<TokenResponse> login(LoginUserRequest loginUserRequest);

    WebResponse<String> logoutUser(User user);

    WebResponse<TokenResponse> verifyToken(GoogleLoginTokenRequest googleLoginTokenRequest);
}
