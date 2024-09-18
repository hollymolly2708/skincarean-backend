package com.skincareMall.skincareMall.model.user.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(max = 100)
    private String fullName;
    @Size(max = 100)
    private String address;
    @Size(max = 100)
    private String email;
    @Size(max = 100)
    private String phone;
}
