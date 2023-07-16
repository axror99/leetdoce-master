package com.example.leetdoce.controller;

import com.example.leetdoce.convertor.from.ConvertResponseFromSolution;
import com.example.leetdoce.convertor.to.ConvertRequestToSolution;
import com.example.leetdoce.dto.request.SolutionRequest;
import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.SolutionResponse;
import com.example.leetdoce.entity.SolutionsEntity;
import com.example.leetdoce.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solution")
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createSolution( @RequestBody SolutionRequest solutionRequest){
        SolutionsEntity solution = ConvertRequestToSolution.getInstance().makeRequestToSolution(solutionRequest);
        solutionService.saveSolution(solution,solutionRequest.getUserId());
        return new ApiResponse<>("your solution was accepted");
    }

    @GetMapping("/list/{id}") // question id
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<SolutionResponse>> getSolutionList(@PathVariable int id){
        List<SolutionsEntity> solutionList = solutionService.getSolutionList(id);
        List<SolutionResponse> solutionResponses = ConvertResponseFromSolution.makeResponseFromSolution(solutionList);
        return new ApiResponse<>("solutions list",solutionResponses);
    }

    @GetMapping("/get/{id}")
    public ApiResponse<SolutionResponse> getOneSolution(@PathVariable int id){
        SolutionsEntity oneSolution = solutionService.getOneSolution(id);
        SolutionResponse solutionResponse = ConvertResponseFromSolution.makeResponseFromSolution(oneSolution);
        return new ApiResponse<>("one solution is here",solutionResponse);
    }

}
