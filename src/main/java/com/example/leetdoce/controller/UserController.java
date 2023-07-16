package com.example.leetdoce.controller;

import com.example.leetdoce.dto.request.UserUpdate;
import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.UserRatingResponse;
import com.example.leetdoce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Void> updateUser(@PathVariable int id, @ModelAttribute UserUpdate userUpdate){
        userService.updateUser(id,userUpdate);
        return new ApiResponse<>("user was successfully updated");
    }

    @GetMapping("/get/rating/{page}/{count}")
    public ApiResponse<List<UserRatingResponse>> getUsersRateList(@PathVariable int page,@PathVariable int count){
        List<UserRatingResponse> usersRating = userService.getUsersRating(page,count);
        return new ApiResponse<>("rating degree lists :", usersRating);
    }
//    Pageable page = PageRequest.of(page1-1,size, Sort.by("id"));
//        return blogRepository.findByCategoryEntity_Id(id,page).getContent();
}
