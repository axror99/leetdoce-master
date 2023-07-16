package com.example.leetdoce.controller;

import com.example.leetdoce.convertor.from.ConvertResponseFromTestCase;
import com.example.leetdoce.convertor.to.ConvertRequestToTestCase;
import com.example.leetdoce.dto.request.TestCaseRequest;
import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.TestCaseResponse;
import com.example.leetdoce.entity.TestCaseEntity;
import com.example.leetdoce.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/testcase/")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping("/create/{id}") // question id
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createTestCase(@PathVariable int id, @RequestBody TestCaseRequest caseRequest){
        TestCaseEntity caseEntity = ConvertRequestToTestCase.makeRequestToTestCase(caseRequest);
        testCaseService.createTestCase(id,caseEntity);
        return new ApiResponse<>("test case was saved successfully");
    }

    @GetMapping("/list/{id}") // question id
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<TestCaseResponse>> getListTestCase(@PathVariable int id){
        List<TestCaseEntity> testCaseList = testCaseService.getTestCaseList(id);
        List<TestCaseResponse> testCaseResponses = ConvertResponseFromTestCase.makeResponseFromQuestion(testCaseList);
        return new ApiResponse<>("test case list",testCaseResponses);
    }

    @PutMapping("/update/{idT}") // test id
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Void> updateTestCase( @PathVariable int idT, @RequestBody TestCaseRequest caseRequest){
        TestCaseEntity caseEntity = ConvertRequestToTestCase.makeRequestToTestCase(caseRequest);
        testCaseService.updateTestCase(idT, caseEntity);
        return new ApiResponse<>("test case was updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteTestCase(@PathVariable int id){
        testCaseService.deleteTestCase(id);
        return new ApiResponse<>(MessageFormat.format(" id = {0} test case was deleted",id));
    }
}
