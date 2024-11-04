package com.skincarean.skincarean.controller.user;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.user.request.UpdateUserRequest;
import com.skincarean.skincarean.model.user.response.UserResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping(path = "/api/users/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> getUser(User user) {
        return userServiceImpl.getUser(user);

    }


    @PatchMapping(path = "/api/users/current-user", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> updateUser(User user,
                                                @RequestParam(required = false) String fullName,
                                                @RequestParam(required = false) String address,
                                                @RequestParam(required = false) String email,
                                                @RequestParam(required = false) String phone) {

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setAddress(address);
        updateUserRequest.setPhone(phone);
        updateUserRequest.setEmail(email);
        updateUserRequest.setFullName(fullName);
        return userServiceImpl.updateUser(user, updateUserRequest);


    }
}
