package com.example.leetdoce.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLogin {
    @Email
    private String email;
    private String password;
}
