package com.skincarean.skincarean.service.user;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.user.request.UpdateUserRequest;
import com.skincarean.skincarean.model.user.response.UserResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.repository.UserRepository;
import com.skincarean.skincarean.utils.Utilities;
import com.skincarean.skincarean.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;


    public WebResponse<UserResponse> getUser(User user) {
        return WebResponse.<UserResponse>builder().data(UserResponse.builder()
                .username(user.getUsernameUser())
                .address(user.getAddress())
                .phone(user.getPhone())
                .lastUpdatedAt(user.getLastUpdatedAt())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .token(user.getToken())
                .profilePicture(user.getPhotoProfile())
                .createdAt(user.getCreatedAt())
                .lastUpdatedAt(user.getLastUpdatedAt())
                .tokenExpiredAt(Utilities.changeFormatToTimeStamp(user.getTokenExpiredAt()))
                .tokenCreatedAt(Utilities.changeFormatToTimeStamp(user.getTokenCreatedAt()))
                .email(user.getEmail())
                .build()
        ).isSuccess(true).build();
    }

    @Transactional
    public WebResponse<UserResponse> updateUser(User user, UpdateUserRequest request) {
        validationService.validate(request);
        if (Objects.nonNull(request.getFullName())) {
            user.setFullName(request.getFullName());
        }
        if (Objects.nonNull(request.getPhone())) {
            user.setPhone(request.getPhone());
        }
        if (Objects.nonNull(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (Objects.nonNull(request.getAddress())) {
            user.setAddress(request.getAddress());
        }
        user.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        try {
            userRepository.save(user);
            return WebResponse
                    .<UserResponse>builder()
                    .data(UserResponse.builder()
                            .fullName(user.getFullName())
                            .address(user.getAddress())
                            .email(user.getEmail())
                            .phone(user.getPhone())
                            .token(user.getToken())
                            .createdAt(user.getCreatedAt())
                            .lastUpdatedAt(user.getLastUpdatedAt())
                            .address(user.getAddress())
                            .tokenCreatedAt(Utilities.changeFormatToTimeStamp(user.getTokenCreatedAt()))
                            .lastUpdatedAt(user.getLastUpdatedAt())
                            .tokenExpiredAt(Utilities.changeFormatToTimeStamp(user.getTokenExpiredAt()))
                            .username(user.getUsernameUser())
                            .build()).isSuccess(true)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.<UserResponse>builder().isSuccess(false).errors("Kesalahan terjadi ketika mengupdate data user kedalam database").build();
        }

    }
}
