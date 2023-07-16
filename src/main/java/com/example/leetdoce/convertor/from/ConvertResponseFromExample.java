package com.example.leetdoce.convertor.from;

import com.example.leetdoce.dto.response.ExampleResponse;
import com.example.leetdoce.entity.ExampleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ConvertResponseFromExample {

    public List<ExampleResponse> makeResponseFromQuestion(List<ExampleEntity> exampleList){
        return exampleList.stream().map(ConvertResponseFromExample::makeResponseFromExample).toList();
    }

    public ExampleResponse makeResponseFromExample(ExampleEntity example){
        return  ExampleResponse.builder()
                .input(example.getInput())
                .output(example.getOutput())
                .explanation(example.getExplanation())
                .build();
    }
}
