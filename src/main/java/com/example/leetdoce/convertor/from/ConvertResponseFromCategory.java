package com.example.leetdoce.convertor.from;

import com.example.leetdoce.dto.response.CategoryResponse;
import com.example.leetdoce.entity.CategoryEntity;

import java.util.List;

public class ConvertResponseFromCategory {

    private static final ThreadLocal<ConvertResponseFromCategory> threadLocalInstance = new ThreadLocal<>();

    private ConvertResponseFromCategory() {}

    public static ConvertResponseFromCategory getInstance() {
        ConvertResponseFromCategory instance = threadLocalInstance.get();
        if (instance == null) {
            instance = new ConvertResponseFromCategory();
            threadLocalInstance.set(instance);
        }
        return instance;
    }

    public CategoryResponse makeResponseFromCategory(CategoryEntity category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .topicList(ConvertResponseFromTopic.makeResponseFromTopic(category.getTopicList()))
                .build();
    }

    public List<CategoryResponse> makeResponseFromCategory(List<CategoryEntity> list){
        return list.stream().map(this::makeResponseFromCategory).toList();
    }
}
