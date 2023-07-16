package com.example.leetdoce.repository;

import com.example.leetdoce.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media,Integer> {

    Optional<Media> findByName(String picName);

}
