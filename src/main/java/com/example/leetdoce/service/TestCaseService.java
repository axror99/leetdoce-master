package com.example.leetdoce.service;

import com.example.leetdoce.entity.QuestionEntity;
import com.example.leetdoce.entity.TestCaseEntity;
import com.example.leetdoce.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final QuestionService questionService;

    public void createTestCase(int id, TestCaseEntity caseEntity) {
        QuestionEntity oneQuestion = questionService.getOneQuestion(id);
        caseEntity.setQuestion(oneQuestion);
        testCaseRepository.save(caseEntity);
    }

    public void updateTestCase(int idT, TestCaseEntity caseEntity) {
        TestCaseEntity testCase = testCaseRepository.getReferenceById(idT);
        if (caseEntity.getMethodName()!=null && !caseEntity.getMethodName().equals("")){
            testCase.setMethodName(caseEntity.getMethodName());
        }
        if (caseEntity.getResponse()!=null && !caseEntity.getResponse().equals("")){
            testCase.setResponse(caseEntity.getResponse());
        }
        if (caseEntity.getTestCases()!=null){
            testCase.setTestCases(caseEntity.getTestCases());
        }
        if (caseEntity.getReferenceTypes()!=null){
            testCase.setReferenceTypes(caseEntity.getReferenceTypes());
        }
        if (caseEntity.getReturnType()!=null && !caseEntity.getReturnType().equals("")){
            testCase.setReturnType(caseEntity.getReturnType());
        }
        testCaseRepository.save(testCase);
    }

    public List<TestCaseEntity> getTestCaseList(int id) {
        QuestionEntity question = questionService.getOneQuestion(id);
        return testCaseRepository.findAllByQuestion(question);
    }

    public void deleteTestCase(int id) {
        testCaseRepository.deleteById(id);
    }
}
