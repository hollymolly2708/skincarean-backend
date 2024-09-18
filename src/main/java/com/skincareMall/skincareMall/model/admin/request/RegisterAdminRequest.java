package com.skincareMall.skincareMall.model.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterAdminRequest {
    @Size(max = 100)
    @NotBlank
    private String username;
    @Size(max = 100)
    @NotBlank
    private String password;
    @Size(max = 100)
    @NotBlank
    private String fullName;
    @Size(max = 100)
    @NotBlank
    private String address;
    @Size(max = 100)
    @NotBlank
    private String phone;
    @Size(max = 100)
    @NotBlank
    private String email;
    private Boolean isAdmin;
}
