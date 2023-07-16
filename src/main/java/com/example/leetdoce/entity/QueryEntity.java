package com.example.leetdoce.entity;

import com.example.leetdoce.simple_class.ListToJsonConverterForInteger;
import com.example.leetdoce.simple_class.MapToJsonConverterForString;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class QueryEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapToJsonConverterForString.class)
    private Map<String,String> definitionTables;

    @Column(unique = true)
    private String schemaName;
    @Column(columnDefinition = "TEXT")
    private String definition;
    @Column(columnDefinition = "TEXT")
    private String ddl;
    @Column(columnDefinition = "TEXT")
    private String dml;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapToJsonConverterForString.class)
    private Map<String,String> console;

    @Column(columnDefinition = "TEXT")
    private String correctAnswer;

    private String level;

    private int like1;

    private int dislike;
    @Column(columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverterForInteger.class)
    private List<Integer> userLikes;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverterForInteger.class)
    private List<Integer> userDisLikes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ExampleEntity> exampleEntityList;

}
