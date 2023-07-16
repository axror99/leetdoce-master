package com.example.leetdoce.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestCaseRequest {

    private String methodName;
    private Map<Integer,String> referenceTypes;
    private Map<Integer,String> testCases;
    private String returnType;
    private String response;
}
