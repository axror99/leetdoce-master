package com.example.leetdoce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SolutionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String console;
    private String definition;
    private long runtime;
    private LocalDate localDate;
    @OneToOne
    private UserEntity user;
    private int questionId;
}
