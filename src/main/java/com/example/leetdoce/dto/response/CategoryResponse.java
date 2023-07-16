package com.example.leetdoce.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryResponse {

    private int id;
    private String name;
    private List<TopicResponseWithoutQuestion> topicList;
}
