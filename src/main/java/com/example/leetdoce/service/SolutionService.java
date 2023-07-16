package com.example.leetdoce.service;

import com.example.leetdoce.entity.SolutionsEntity;
import com.example.leetdoce.entity.UserEntity;
import com.example.leetdoce.exception.NotFoundException;
import com.example.leetdoce.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final UserService userService;
    private final QuestionService questionService;

    public void saveSolution(SolutionsEntity solution, int userId) {
        UserEntity user = userService.getUserById(userId);
        Optional<SolutionsEntity> entityOptional = solutionRepository.findByUserAndQuestionId(user, solution.getQuestionId());
        if (entityOptional.isPresent()) {
            SolutionsEntity entity = entityOptional.get();
            entity.setDefinition(solution.getDefinition());
            entity.setConsole(solution.getConsole());
            entity.setRuntime(solution.getRuntime());
            entity.setLocalDate(solution.getLocalDate());
            solutionRepository.save(entity);
        }else {
            solution.setUser(user);
            solutionRepository.save(solution);
        }

    }

    public List<SolutionsEntity> getSolutionList(int id) {
        return solutionRepository.findByQuestionId(id);
    }

    public SolutionsEntity getOneSolution(int id) {
        return solutionRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("id = {0} solution was not found in DB", id)));
    }
}
