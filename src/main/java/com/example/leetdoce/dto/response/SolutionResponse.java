package com.example.leetdoce.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SolutionResponse {

    private int id;
    private String console;
    private String definition;
    private long runtime;
    private LocalDate localDate;
    private String userName;
    private int userId;
    private String picture;
}
