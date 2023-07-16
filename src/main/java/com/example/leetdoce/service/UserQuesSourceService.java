package com.example.leetdoce.service;

import com.example.leetdoce.entity.UserQuestionSource;
import com.example.leetdoce.repository.UserQuesSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserQuesSourceService {

    private final UserQuesSourceRepository quesSourceRepository;

    public UserQuestionSource saveQuesSource(UserQuestionSource source){
        source.setUuid(UUID.randomUUID());
        return quesSourceRepository.saveAndFlush(source);
//        return quesSourceRepository.save(source);
    }
}
