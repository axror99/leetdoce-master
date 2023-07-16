package com.example.leetdoce.convertor.to;

import com.example.leetdoce.dto.request.CategoryRequest;
import com.example.leetdoce.entity.CategoryEntity;

public class ConvertRequestToCategory {

    private static final ThreadLocal<ConvertRequestToCategory> instanceLocal = new ThreadLocal<>();
    private ConvertRequestToCategory(){}

    public static ConvertRequestToCategory getInstance() {
        ConvertRequestToCategory instance = instanceLocal.get();
        if (instance == null){
            instance = new ConvertRequestToCategory();
            instanceLocal.set(instance);
        }
        return instance;
    }

    public CategoryEntity fromCategoryRequest(CategoryRequest categoryRequest){
        CategoryEntity category= new CategoryEntity();
        category.setName(categoryRequest.getName());
        return category;
    }
}
