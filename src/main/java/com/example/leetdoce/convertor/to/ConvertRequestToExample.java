package com.example.leetdoce.convertor.to;

import com.example.leetdoce.dto.request.ExampleRequest;
import com.example.leetdoce.entity.ExampleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ConvertRequestToExample {

    public List<ExampleEntity> makeRequestToQuestion(List<ExampleRequest> exampleList){
        return exampleList.stream().map(ConvertRequestToExample::makeRequestToQuestion).toList();
    }

    public ExampleEntity makeRequestToQuestion(ExampleRequest exampleRequest){
        return ExampleEntity.builder()
                .input(exampleRequest.getInput())
                .output(exampleRequest.getOutput())
                .explanation(exampleRequest.getExplanation())
                .build();
    }
}
