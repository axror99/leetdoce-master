package com.example.leetdoce.convertor.to;

import com.example.leetdoce.dto.request.UserRegister;
import com.example.leetdoce.dto.response.ReturnUserWithToken;
import com.example.leetdoce.entity.UserEntity;

import java.time.LocalDate;

public class ConvertRequestToUser {

    private static final ThreadLocal<ConvertRequestToUser> instanceLocal = new ThreadLocal<>();
    private ConvertRequestToUser(){}

    public static ConvertRequestToUser getInstance() {
        ConvertRequestToUser instance = instanceLocal.get();
        if (instance == null){
            instance = new ConvertRequestToUser();
            instanceLocal.set(instance);
        }
        return instance;
    }
    public UserEntity fromUserRegister(UserRegister userRegister) {
        return UserEntity.builder()
                .email(userRegister.getEmail())
                .name(userRegister.getName())
                .joinTime(LocalDate.now())
                .build();
    }

    public ReturnUserWithToken fromUserEntity(UserEntity user){
        return  ReturnUserWithToken.builder()
                .id(user.getId())
                .picture(user.getPicture())
                .name(user.getName())
                .email(user.getEmail())
                .roleEntities(user.getRoleEntities())
                .joinTime(user.getJoinTime())
                .build();
    }
}
