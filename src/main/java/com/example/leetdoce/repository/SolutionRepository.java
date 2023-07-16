package com.example.leetdoce.repository;

import com.example.leetdoce.entity.SolutionsEntity;
import com.example.leetdoce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionsEntity,Integer> {

    Optional<SolutionsEntity> findByUserAndQuestionId(UserEntity user, int idQ);

    List<SolutionsEntity> findByQuestionId(int id);
}
