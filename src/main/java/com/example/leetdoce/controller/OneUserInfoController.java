package com.example.leetdoce.controller;

import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.UserResponse;
import com.example.leetdoce.entity.UserEntity;
import com.example.leetdoce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/me")
@RequiredArgsConstructor
public class OneUserInfoController {

    private final UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserResponse> getInfoAboutMe(
    ){
        UserEntity principal = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponse build = UserResponse.builder()
                .id(principal.getId())
                .joinTime(principal.getJoinTime())
                .name(principal.getName())
                .picture(principal.getPicture())
                .roleEntities(principal.getRoleEntities())
                .email(principal.getEmail())
                .build();
        return new ApiResponse<>("one user info ",build);
    }
}
