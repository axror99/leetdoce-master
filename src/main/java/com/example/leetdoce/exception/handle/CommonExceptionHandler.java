package com.example.leetdoce.exception.handle;

import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Controller
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistException.class,
            CategoryAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> userAlreadyExist(Exception e){
        return new ApiResponse<>(e.getMessage());
    }

    @ExceptionHandler({
            NotFoundException.class,
            RoleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> userNotFound(Exception e){
        return new ApiResponse<>(e.getMessage());
    }

    @ExceptionHandler(GetBytesException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ApiResponse<Void> problemInGetBytes(GetBytesException e){
        return new ApiResponse<>(e.getMessage());
    }
    @ExceptionHandler({JwtException.class,IOException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Void> handleJwtExpired(ExpiredJwtException e) {
        return new ApiResponse<>(e.getMessage());
    }

}
