package com.example.leetdoce.repository;

import com.example.leetdoce.entity.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleEntity,Integer> {
}
