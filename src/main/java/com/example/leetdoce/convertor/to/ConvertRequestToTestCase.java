package com.example.leetdoce.convertor.to;

import com.example.leetdoce.dto.request.TestCaseRequest;
import com.example.leetdoce.entity.TestCaseEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConvertRequestToTestCase {

    public TestCaseEntity makeRequestToTestCase(TestCaseRequest testCaseRequest){
        TestCaseEntity testCaseEntity1 = new TestCaseEntity();

        testCaseEntity1.setTestCases(testCaseRequest.getTestCases());
        testCaseEntity1.setReferenceTypes(testCaseRequest.getReferenceTypes());
        testCaseEntity1.setResponse(testCaseRequest.getResponse());
        testCaseEntity1.setMethodName(testCaseRequest.getMethodName());
        testCaseEntity1.setReturnType(testCaseRequest.getReturnType());
        return testCaseEntity1;
    }
}
