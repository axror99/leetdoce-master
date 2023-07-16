package com.example.leetdoce.dto.response;

import lombok.*;

import java.util.Map;
@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TestCaseResponse {

    private int id;
    private String methodName;
    private Map<Integer,String> referenceTypes;
    private Map<Integer,String> testCases;
    private String returnType;
    private String response;
}
