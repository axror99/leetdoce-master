package com.example.leetdoce.convertor.from;

import com.example.leetdoce.dto.response.TopicResponse;
import com.example.leetdoce.dto.response.TopicResponseWithoutQuestion;
import com.example.leetdoce.entity.TopicEntity;
import com.example.leetdoce.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ConvertResponseFromTopic {

    public List<TopicResponseWithoutQuestion> makeResponseFromTopic(List<TopicEntity> topicList){
        return topicList.stream().map(ConvertResponseFromTopic::makeResponseFromTopic).toList();
    }

    public TopicResponseWithoutQuestion makeResponseFromTopic(TopicEntity topic){
        return TopicResponseWithoutQuestion.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }
    public List<TopicResponse> makeResponseFromFullTopic(List<TopicEntity> topicList){
        return topicList.stream().map(ConvertResponseFromTopic::makeResponseFromFullTopic).toList();
    }

    public TopicResponse makeResponseFromFullTopic(TopicEntity topic){
        if (topic.getQueryList().isEmpty()) {
            return TopicResponse.builder()
                    .id(topic.getId())
                    .name(topic.getName())
                    .questionList(ConvertResponseFromQuestion.makeResponseFromQuestion(topic.getQuestionList()))
                    .build();
        }else {
            return TopicResponse.builder()
                    .id(topic.getId())
                    .name(topic.getName())
                    .questionList(ConvertResponseFromQuery.makeResponseFromQuery(topic.getQueryList()))
                    .build();
        }
    }
    public TopicResponse makeTopicResponseForUser(TopicEntity topic, UserEntity user) {
        if (topic.getQueryList().isEmpty()) {
            return TopicResponse.builder()
                    .id(topic.getId())
                    .name(topic.getName())
                    .questionList(ConvertResponseFromQuestion.makeResponseFromQuestion(topic.getQuestionList(), user.getUserQuestionSourceList()))
                    .build();
        }else {
            return TopicResponse.builder()
                    .id(topic.getId())
                    .name(topic.getName())
                    .questionList(ConvertResponseFromQuery.makeResponseFromQuery(topic.getQueryList(), user.getUserQuestionSourceList()))
                    .build();
        }
    }
}
