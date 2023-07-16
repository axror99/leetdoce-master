package com.example.leetdoce.convertor.from;

import com.example.leetdoce.dto.response.TestCaseResponse;
import com.example.leetdoce.entity.TestCaseEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ConvertResponseFromTestCase {

    public List<TestCaseResponse> makeResponseFromQuestion(List<TestCaseEntity> testCaseEntity1List){

        return testCaseEntity1List.stream().map(ConvertResponseFromTestCase::makeResponseFromQuestion).toList();
    }

    public TestCaseResponse makeResponseFromQuestion(TestCaseEntity testCase){
        return TestCaseResponse.builder()
                .id(testCase.getId())
                .methodName(testCase.getMethodName())
                .testCases(testCase.getTestCases())
                .referenceTypes(testCase.getReferenceTypes())
                .returnType(testCase.getReturnType())
                .response(testCase.getResponse())
                .build();
    }
}
