package com.skincareMall.skincareMall.controller.google_auth;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.skincareMall.skincareMall.model.google_auth.request.TokenRequest;
import com.skincareMall.skincareMall.model.google_auth.response.VerificationResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.google_auth.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleAuthController {

    // Ganti dengan Web client ID yang benar
    private static final String CLIENT_ID = "671324987024-bc5r4pm5vsmuers2otis2h5bot0tj0bb.apps.googleusercontent.com";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Autowired
    private GoogleAuthService googleAuthService;

    @PostMapping("/api/google-auth/verifyToken")
    public WebResponse<VerificationResponse> verifyToken(@RequestBody TokenRequest tokenRequest) {
        VerificationResponse verificationResponse = googleAuthService.verifyToken(tokenRequest);
        return WebResponse.<VerificationResponse>builder().data(verificationResponse).build();
    }
}