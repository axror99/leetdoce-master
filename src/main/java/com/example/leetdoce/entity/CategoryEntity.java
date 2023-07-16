package com.example.leetdoce.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryEntity extends AuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "category")
    private List<TopicEntity> topicList;
}
