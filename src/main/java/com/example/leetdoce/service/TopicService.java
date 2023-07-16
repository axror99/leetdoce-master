package com.example.leetdoce.service;

import com.example.leetdoce.dto.request.TopicRequest;
import com.example.leetdoce.entity.CategoryEntity;
import com.example.leetdoce.entity.QuestionEntity;
import com.example.leetdoce.entity.TopicEntity;
import com.example.leetdoce.exception.NotFoundException;
import com.example.leetdoce.repository.CategoryRepository;
import com.example.leetdoce.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final CategoryRepository categoryRepository;


    public void createTopic(int id, TopicEntity topic){
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} category wa not found in database", id)));
        List<TopicEntity> topicList = category.getTopicList();
        topicList.add(topic);
        category.setTopicList(topicList);
        topic.setCategory(category);
        topicRepository.save(topic);
    }

    public void updateTopic(int id, TopicRequest topic) {
        TopicEntity topicEntity1 = topicRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} topic was not found in database", id)));
        if (topic.getName()!=null && !topic.getName().equals("")){
            topicEntity1.setName(topic.getName());
        }
        if (topic.getId()!=0){
            CategoryEntity category = categoryRepository.findById(topic.getId()).orElseThrow(() ->
                    new NotFoundException(MessageFormat.format("id = {0} category was not found in database", topic.getId())));
            topicEntity1.setCategory(category);
        }
        topicRepository.save(topicEntity1);
    }

    public List<TopicEntity> getTopicList(int id) {
         return topicRepository.findAllByCategory_Id(id);
    }

    public TopicEntity getOneTopic(int id) {
       return topicRepository.findById(id).orElseThrow(()->
                new NotFoundException(MessageFormat.format("id = {0} topic was not found in database",id)));

    }

    public void deleteTopic(int id) {
        TopicEntity topic = topicRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} topic was not found in database", id)));
        topicRepository.delete(topic);
    }

    public void saveTopic(TopicEntity topic){
        topicRepository.save(topic);
    }

    public void deleteQuestionFromTopic(QuestionEntity question, int topicId) {
        TopicEntity oneTopic = getOneTopic(topicId);
        boolean remove = oneTopic.getQuestionList().remove(question);
        if (remove){
            System.out.println("question was deleted");
        }else {
            System.out.println("question was not deleted");
        }
        topicRepository.save(oneTopic);
    }
}
