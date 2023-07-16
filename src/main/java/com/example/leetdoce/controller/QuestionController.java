package com.example.leetdoce.controller;

import com.example.leetdoce.compilator.ResponseCompilator;
import com.example.leetdoce.convertor.to.ConvertRequestToQuestion;
import com.example.leetdoce.dto.request.QuestionRequestForCreate;
import com.example.leetdoce.dto.request.QuestionRequestForUpdate;
import com.example.leetdoce.dto.request.UserQuestion;
import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.QuestionForUser;
import com.example.leetdoce.entity.QuestionEntity;
import com.example.leetdoce.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question/")
@RequiredArgsConstructor
@CrossOrigin
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create/{id}") // topic id
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createQuestion(@PathVariable int id, @RequestBody QuestionRequestForCreate questionRequestForCreate){
        QuestionEntity question = ConvertRequestToQuestion.getInstance().fromQuestionRequest(questionRequestForCreate);
        questionService.createQuestion(id, question);
        return new ApiResponse<>("question was created successfully");
    }

    @PutMapping("/update/{id}") // question id
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Void> updateQuestion(@PathVariable int id,@RequestBody QuestionRequestForUpdate questionRequestForUpdate){
        QuestionEntity question = ConvertRequestToQuestion.getInstance().fromQuestionRequest(questionRequestForUpdate);
        int topicId = questionRequestForUpdate.getTopicId();
        questionService.updateQuestion(id,topicId , question);
        return new ApiResponse<>("question was updated successfully");
    }

    @GetMapping("/get/{id}/{userId}") // question id / user id
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<QuestionForUser> getOneQuestion(@PathVariable int id, @PathVariable int userId){
        QuestionForUser userQuestion = questionService.getUserQuestion(id, userId);
        return new ApiResponse<>("your Query question had been delivered",userQuestion);
    }

    // test case save qilgandan keyin tekshir

    @PostMapping("/submit/{id}") // question id
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<ResponseCompilator> submitQuestion(@PathVariable int id, @RequestBody UserQuestion userQuestion) throws Exception {
        ResponseCompilator responseCompilator = questionService.submitQuestion(id, userQuestion);
        return new ApiResponse<>("compilator run successfully",responseCompilator);
    }

    @DeleteMapping("/delete/{id}/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteQuestion(@PathVariable int id, @PathVariable int topicId){
        questionService.deleteQuestion(id,topicId);
        return new ApiResponse<>("Question was deleted successfully");
    }

    @PutMapping("/reset/{id}/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<QuestionForUser> resetQuestion(@PathVariable int id,@PathVariable int userId){
        QuestionForUser userQuestion = questionService.resetQuestion(id, userId);
        return new ApiResponse<>("question was reset successfully",userQuestion);
    }

    @PutMapping("/button/like/{idQ}/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Void> buttonLike(@PathVariable int idQ,@PathVariable int userId){
        questionService.buttonLike(idQ,userId);
        return new ApiResponse<>("your LIKE was saved successfully");
    }

    @PutMapping("/button/dislike/{idQ}/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Void> buttonDisLike(@PathVariable int idQ,@PathVariable int userId){
        questionService.buttonDisLike(idQ,userId);
        return new ApiResponse<>("your DisLIKE was saved successfully");
    }
}
