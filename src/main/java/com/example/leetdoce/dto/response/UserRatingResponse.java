package com.example.leetdoce.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserRatingResponse {

    private int id;
    private String name;
    private String picture;
    private int hard;
    private int medium;
    private int easy;
}
