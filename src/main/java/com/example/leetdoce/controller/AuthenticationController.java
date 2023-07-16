package com.example.leetdoce.controller;

import com.example.leetdoce.dto.request.UserLogin;
import com.example.leetdoce.dto.request.UserRegister;
import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.ReturnUserWithToken;
import com.example.leetdoce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReturnUserWithToken> register(@Valid @RequestBody UserRegister userRegister)
    {
        ReturnUserWithToken user=userService.registerUser(userRegister);
        return new ApiResponse<>("registered successfully", user);
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ReturnUserWithToken> login(@RequestBody UserLogin userLogin) {
        ReturnUserWithToken  user = userService.login(userLogin);
        return new ApiResponse<>("login was passed successfully",user);
    }
}
