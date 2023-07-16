package com.example.leetdoce.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ExampleEntity extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition="TEXT")
    private String input;
    @Column(columnDefinition="TEXT")
    private String output;
    @Column(columnDefinition="TEXT")
    private String explanation;
//    @ManyToOne
//    private QuestionEntity question;
}
