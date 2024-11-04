package com.skincarean.skincarean.service.user;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.user.request.UpdateUserRequest;
import com.skincarean.skincarean.model.user.response.UserResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;

public interface UserService {
    WebResponse<UserResponse> getUser(User user);

    WebResponse<UserResponse> updateUser(User user, UpdateUserRequest request);
}
