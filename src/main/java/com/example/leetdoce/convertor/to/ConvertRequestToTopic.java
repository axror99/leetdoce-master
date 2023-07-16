package com.example.leetdoce.convertor.to;

import com.example.leetdoce.dto.request.TopicRequest;
import com.example.leetdoce.entity.TopicEntity;

public class ConvertRequestToTopic {
    private static final ThreadLocal<ConvertRequestToTopic> instanceLocal = new ThreadLocal<>();
    private ConvertRequestToTopic(){}

    public static ConvertRequestToTopic getInstance() {
        ConvertRequestToTopic instance = instanceLocal.get();
        if (instance == null){
            instance= new ConvertRequestToTopic();
            instanceLocal.set(instance);
        }
        return instance;
    }

    public TopicEntity fromTopicRequest(TopicRequest topicRequest){
        TopicEntity topic= new TopicEntity();
        topic.setName(topicRequest.getName());
        return topic;
    }
}
