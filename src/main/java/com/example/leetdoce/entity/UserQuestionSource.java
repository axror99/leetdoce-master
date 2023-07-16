package com.example.leetdoce.entity;

import com.example.leetdoce.simple_class.MapToJsonConverterForString;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserQuestionSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapToJsonConverterForString.class)
    private Map<String,String> writtenConsole;
    private Long runtime;
    private int positionQuestion;
    private int questionID;
    private int queryID;
    private String level;
    private UUID uuid;
    // purpose of uuid is to update
}
