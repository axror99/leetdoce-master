package com.example.leetdoce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>{

    private String message;
    private T data;

    public ApiResponse(String message) {
        this.message = message;
    }
}
