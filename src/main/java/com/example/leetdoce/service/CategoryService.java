package com.example.leetdoce.service;

import com.example.leetdoce.entity.CategoryEntity;
import com.example.leetdoce.exception.CategoryAlreadyExistException;
import com.example.leetdoce.exception.NotFoundException;
import com.example.leetdoce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(CategoryEntity category) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(category.getName());
        if (categoryEntity.isPresent()){
            throw new CategoryAlreadyExistException(MessageFormat.format("name = {0} category is  exist in database", category.getName()));
        }
        categoryRepository.save(category);
    }

    public void updateCategory(int id , CategoryEntity category) {
        CategoryEntity categoryEntity1 = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("id = {0} category wa not found in database", id)));
        if (category.getName()!=null && !category.getName().equals("")){
            categoryEntity1.setName(category.getName());
        }
        categoryRepository.save(categoryEntity1);
    }

    public void deleteCategory(int id) {
        CategoryEntity categoryEntity1 = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("id = {0} category wa not found in database", id)));
        categoryRepository.delete(categoryEntity1);
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }


}
