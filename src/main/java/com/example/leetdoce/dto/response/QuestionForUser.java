package com.example.leetdoce.dto.response;

import lombok.*;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QuestionForUser {

    private int id;
    private String name;
    private String definition;
    private Map<String,String> console;
    private String level;
    private int like1;
    private int dislike;
    private String solved;
    private List<Integer> userInLikes;
    private List<Integer> userInDisLikes;
    private List<ExampleResponse> exampleList;
}
