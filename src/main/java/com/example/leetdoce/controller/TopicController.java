package com.example.leetdoce.controller;

import com.example.leetdoce.convertor.from.ConvertResponseFromTopic;
import com.example.leetdoce.convertor.to.ConvertRequestToTopic;
import com.example.leetdoce.dto.request.TopicRequest;
import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.TopicResponse;
import com.example.leetdoce.dto.response.TopicResponseWithoutQuestion;
import com.example.leetdoce.entity.TopicEntity;
import com.example.leetdoce.entity.UserEntity;
import com.example.leetdoce.service.TopicService;
import com.example.leetdoce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("topic/")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final UserService userService;

    @PostMapping("/create/{id}") // category Id
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createTopic(@PathVariable int id, @RequestBody TopicRequest topicRequest){
        TopicEntity topic = ConvertRequestToTopic.getInstance().fromTopicRequest(topicRequest);
        topicService.createTopic(id ,topic);
        return new ApiResponse<>("topic was attached to Category successfully");
    }

    @PutMapping("/update/{id}") // topic id
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Void> updateTopic(@PathVariable int id, @RequestBody TopicRequest topicRequest){
        topicService.updateTopic(id, topicRequest);
        return new ApiResponse<>("topic was updated successfully");
    }

    @GetMapping("/list/{id}") // category id
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<TopicResponseWithoutQuestion>> getTopicList(@PathVariable int id){
        List<TopicEntity> topicList = topicService.getTopicList(id);
        List<TopicResponseWithoutQuestion> topicResList = ConvertResponseFromTopic.makeResponseFromTopic(topicList);
        return new ApiResponse<>("topic list was successfully delivered",topicResList);
    }

    @GetMapping("/get/{id}") // topic id to get questions
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TopicResponse> getOneTopic(@PathVariable int id){
        TopicEntity oneTopic = topicService.getOneTopic(id);
        TopicResponse topic = ConvertResponseFromTopic.makeResponseFromFullTopic(oneTopic);
        return new ApiResponse<>("topic was token in DB",topic);
    }
    @GetMapping("/get/user/{id}/{userId}") // topic id to get questions
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TopicResponse> getOneTopic(@PathVariable int id, @PathVariable int userId){
        TopicEntity oneTopic = topicService.getOneTopic(id);
        UserEntity user = userService.getUserById(userId);
        TopicResponse topic = ConvertResponseFromTopic.makeTopicResponseForUser(oneTopic, user);
//        TopicResponse topic = ConvertResponseFromTopic.makeResponseFromFullTopic(oneTopic);
        return new ApiResponse<>("topic was token in DB",topic);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteTopic(@PathVariable int id){
        topicService.deleteTopic(id);
        return new ApiResponse<>("topic was deleted successfully");
    }
}
