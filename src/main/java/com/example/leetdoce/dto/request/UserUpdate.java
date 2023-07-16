package com.example.leetdoce.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UserUpdate {

    private String email;
    private String password;
    private String name;
    private MultipartFile picture;
}
