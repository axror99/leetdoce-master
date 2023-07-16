package com.example.leetdoce.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ExampleResponse {

    private String input;
    private String output;
    private String explanation;

}
