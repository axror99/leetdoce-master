package com.example.leetdoce.dto.response;

import com.example.leetdoce.entity.RoleEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnUserWithToken {

    private int id;
    private String name;
    private String email;
    private LocalDate joinTime;
    private String picture;
    private List<RoleEntity> roleEntities;
    private String token;
}
