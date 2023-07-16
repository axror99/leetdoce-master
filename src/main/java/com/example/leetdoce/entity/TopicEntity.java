package com.example.leetdoce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TopicEntity extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @JsonIgnore
    @ManyToMany
    private List<QuestionEntity> questionList;

    @JsonIgnore
    @ManyToMany
    private List<QueryEntity> queryList;

    @JsonBackReference
    @ManyToOne
    private CategoryEntity category;
}
