package com.skincarean.skincarean.model.admin.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdminRequest {
    @Size(max = 100)
    private String fullName;
    @Size(max = 100)
    private String address;
    @Size(max = 100)
    private String phone;
    @Size(max = 100)
    private String email;
}
