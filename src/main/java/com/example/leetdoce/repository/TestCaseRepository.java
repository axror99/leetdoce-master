package com.example.leetdoce.repository;

import com.example.leetdoce.entity.QuestionEntity;
import com.example.leetdoce.entity.TestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCaseEntity,Integer> {

    List<TestCaseEntity> findAllByQuestion(QuestionEntity question);
}
