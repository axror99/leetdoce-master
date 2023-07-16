package com.example.leetdoce.repository;

import com.example.leetdoce.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity,Integer> {
    List<TopicEntity> findAllByCategory_Id(Integer id);
}
