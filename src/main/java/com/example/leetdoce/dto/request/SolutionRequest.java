package com.example.leetdoce.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SolutionRequest {

    private String console;
    private String definition;
    private long runtime;
    private LocalDate localDate;
    private int userId;
    private int questionId;
}
