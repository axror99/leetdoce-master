package com.example.leetdoce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class QueryRequestForUpdate {

    String name;
    Map<String,String> tableDefinition;
    String schemaName;
    String definition;
    String ddl;
    String dml;
    List<ExampleRequest> exampleList;
    String correctAnswer;
    String level;
    Map<String,String> console;
}
