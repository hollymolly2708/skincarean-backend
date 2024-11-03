package com.skincareMall.skincareMall.service.user;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.user.request.UpdateUserRequest;
import com.skincareMall.skincareMall.model.user.response.UserResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;

public interface UserService {
    WebResponse<UserResponse> getUser(User user);

    WebResponse<UserResponse> updateUser(User user, UpdateUserRequest request);
}
