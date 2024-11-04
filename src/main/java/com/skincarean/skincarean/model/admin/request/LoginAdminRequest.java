package com.skincarean.skincarean.model.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginAdminRequest {
    @NotBlank
    @Size(max = 100)
    private String username;
    @NotBlank
    @Size(max = 100)
    private String password;
}
