package com.example.leetdoce.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionRequestForCreate {

    String name;
    String definition;
    Map<String,String> console;
    String level;
    List<ExampleRequest> exampleList;
}
