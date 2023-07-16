package com.example.leetdoce.repository;

import com.example.leetdoce.entity.UserQuestionSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuesSourceRepository extends JpaRepository<UserQuestionSource,Integer> {
}
