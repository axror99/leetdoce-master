package com.example.leetdoce.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExampleRequest {

    String input;
    String output;
    String explanation;
}
