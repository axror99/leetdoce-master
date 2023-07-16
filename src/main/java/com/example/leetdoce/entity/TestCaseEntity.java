package com.example.leetdoce.entity;

import com.example.leetdoce.simple_class.MapToJsonConverterForInteger;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestCaseEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String methodName;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapToJsonConverterForInteger.class)
    private Map<Integer,String> referenceTypes;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapToJsonConverterForInteger.class)
    private Map<Integer,String> testCases;

    private String returnType;
    @Column(columnDefinition="TEXT")
    private String response;
    @ManyToOne
    private QuestionEntity question;
}
