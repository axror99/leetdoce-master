package com.example.leetdoce.controller;

import com.example.leetdoce.compilator.ResponseCompilator;
import com.example.leetdoce.convertor.to.ConvertRequestToQuery;
import com.example.leetdoce.dto.request.QueryRequest;
import com.example.leetdoce.dto.request.QueryRequestForUpdate;
import com.example.leetdoce.dto.request.UserQuestion;
import com.example.leetdoce.dto.response.ApiResponse;
import com.example.leetdoce.dto.response.QuestionForUser;
import com.example.leetdoce.entity.QueryEntity;
import com.example.leetdoce.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query/")
@RequiredArgsConstructor
@CrossOrigin
public class QueryController {

    private final QueryService queryService;

    @PostMapping("/create/{id}") // topic id
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createTableWithQuery(@PathVariable int id, @RequestBody QueryRequest queryRequest){
        QueryEntity queryEntity = ConvertRequestToQuery.makeRequestToQuery(queryRequest);
        queryService.createTableUsingQuery(id,queryEntity);
        return new ApiResponse<>("new Table(s) was created successfully");
    }

    @PutMapping("/update/{id}") // query id
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Void> updateTableWithQuery(@PathVariable int id, @RequestBody QueryRequestForUpdate queryRequest){
        queryService.updateQuery(id, queryRequest);
        return new ApiResponse<>("new Table(s) was updated successfully");
    }

    @DeleteMapping("/delete/{idTopic}/{id}") // query id
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteTableWithQuery(@PathVariable int idTopic, @PathVariable int id){
        queryService.deleteQuery(idTopic,id);
        return new ApiResponse<>("new Table(s) was deleted successfully");
    }

    @GetMapping("/schemas")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<String>> getAllSchemas(){
        List<String> allSchemas = queryService.getAllSchemasInDB();
        return new ApiResponse<>("here are your all schemas in DB",allSchemas);
    }

    @PostMapping("/submit/{id}") // question id
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<ResponseCompilator> submitQuestion(@PathVariable int id, @RequestBody UserQuestion userQuestion) throws Exception {
        ResponseCompilator responseCompilator = queryService.submitQuery(id, userQuestion);
        return new ApiResponse<>("compilator run successfully",responseCompilator);
    }

    @GetMapping("/get/{id}/{userId}") // query id / user id
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<QuestionForUser> getOneQuestion(@PathVariable int id, @PathVariable int userId){
        QuestionForUser userQuestion = queryService.getUserQueryQuestion(id, userId);
        return new ApiResponse<>("your question had been delivered",userQuestion);
    }

    @GetMapping("/get/one/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<QueryEntity> getOneOriginalQuery(@PathVariable int id){
        QueryEntity queryQuestion = queryService.getOneQueryQuestion(id);
        return new ApiResponse<>("here are queryEntity object",queryQuestion);
    }

}
