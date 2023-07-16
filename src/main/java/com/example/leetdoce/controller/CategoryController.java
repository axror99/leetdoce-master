package com.example.leetdoce.controller;

import com.example.leetdoce.convertor.from.ConvertResponseFromCategory;
import com.example.leetdoce.convertor.to.ConvertRequestToCategory;
import com.example.leetdoce.dto.request.CategoryRequest;
import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.CategoryResponse;
import com.example.leetdoce.entity.CategoryEntity;
import com.example.leetdoce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryEntity category = ConvertRequestToCategory.getInstance().fromCategoryRequest(categoryRequest);
        categoryService.createCategory(category);
        return new ApiResponse<>("category was created successfully");
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Void> updateCategory(@PathVariable int id ,@RequestBody CategoryRequest categoryRequest){
        CategoryEntity category = ConvertRequestToCategory.getInstance().fromCategoryRequest(categoryRequest);
        categoryService.updateCategory(id, category);
        return  new ApiResponse<>("category was successfully updated");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        return new ApiResponse<>("category eas deleted successfully");
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<CategoryResponse>> listCategories(){
        List<CategoryEntity> allCategories = categoryService.getAllCategories();
        List<CategoryResponse> categoryRes = ConvertResponseFromCategory.getInstance().makeResponseFromCategory(allCategories);
        return new ApiResponse<>("categories were taken in DB successfully",categoryRes);
    }
}
