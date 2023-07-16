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
public class QueryRequest {

    @NotNull
    @NotBlank
    String name;
    @NotEmpty
    Map<String,String> tableDefinition;
    @NotNull
    @NotBlank
    String schemaName;
    @NotNull
    @NotBlank
    String definition;
    @NotNull
    @NotBlank
    String ddl;
    @NotNull
    @NotBlank
    String dml;
    @NotEmpty
    List<ExampleRequest> exampleList;
    @NotNull
    @NotBlank
    String correctAnswer;
    @NotNull
    @NotBlank
    String level;

    Map<String,String> console;
}
