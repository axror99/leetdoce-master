package com.example.leetdoce.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse {

    int id;
    String name;
    List<QuestionResponseWithoutTestCase> questionList;
}
