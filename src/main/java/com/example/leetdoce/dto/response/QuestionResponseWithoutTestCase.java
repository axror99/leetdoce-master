package com.example.leetdoce.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseWithoutTestCase {

    int id;
    String name;
    String level;
    int like1;
    int dislike;
    String solved;
}
