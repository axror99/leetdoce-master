package com.example.leetdoce.convertor.to;

import com.example.leetdoce.dto.request.SolutionRequest;
import com.example.leetdoce.entity.SolutionsEntity;

import java.time.LocalDate;

public class ConvertRequestToSolution {

    private static final ThreadLocal<ConvertRequestToSolution> instanceLocal = new ThreadLocal<>() ;

    public ConvertRequestToSolution(){
    }

    public static ConvertRequestToSolution getInstance(){
        ConvertRequestToSolution solution = instanceLocal.get();
        if (solution == null){
            solution = new ConvertRequestToSolution();
            instanceLocal.set(solution);
        }
        return solution;
    }

    public SolutionsEntity makeRequestToSolution(SolutionRequest request){
        return SolutionsEntity.builder()
                .runtime(request.getRuntime())
                .localDate(LocalDate.now())
                .definition(request.getDefinition())
                .console(request.getConsole())
                .questionId(request.getQuestionId())
                .build();
    }
}
