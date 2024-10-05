package com.skincareMall.skincareMall.model.google_auth.response;

import com.skincareMall.skincareMall.model.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationResponse {
    private boolean valid;
    private UserResponse user;
    private String error;



}
