package com.example.leetdoce.convertor.to;

import com.example.leetdoce.dto.request.QueryRequest;
import com.example.leetdoce.dto.request.QueryRequestForUpdate;
import com.example.leetdoce.entity.QueryEntity;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class ConvertRequestToQuery {

    public QueryEntity makeRequestToQuery(QueryRequest queryRequest){

        return QueryEntity.builder()
                .name(queryRequest.getName())
                .correctAnswer(queryRequest.getCorrectAnswer())
                .ddl(queryRequest.getDdl())
                .level(queryRequest.getLevel())
                .definition(queryRequest.getDefinition())
                .schemaName(queryRequest.getSchemaName())
                .definitionTables(queryRequest.getTableDefinition())
                .exampleEntityList(ConvertRequestToExample.makeRequestToQuestion(queryRequest.getExampleList()))
                .dml(queryRequest.getDml())
                .console(queryRequest.getConsole())
                .userLikes(new ArrayList<>())
                .userDisLikes(new ArrayList<>())
                .build();
    }
}
