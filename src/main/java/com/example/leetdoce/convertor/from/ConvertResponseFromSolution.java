package com.example.leetdoce.convertor.from;

import com.example.leetdoce.dto.response.SolutionResponse;
import com.example.leetdoce.entity.SolutionsEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ConvertResponseFromSolution {

    public List<SolutionResponse> makeResponseFromSolution(List<SolutionsEntity> solutionsEntityList){
        return solutionsEntityList.stream().map(ConvertResponseFromSolution::makeResponseFromSolution).toList();
    }

    public SolutionResponse makeResponseFromSolution(SolutionsEntity solution){
        return SolutionResponse.builder()
                .id(solution.getId())
                .runtime(solution.getRuntime())
                .localDate(solution.getLocalDate())
                .definition(solution.getDefinition())
                .console(solution.getConsole())
                .picture(solution.getUser().getPicture())
                .userId(solution.getUser().getId())
                .userName(solution.getUser().getName())
                .build();
    }
}
