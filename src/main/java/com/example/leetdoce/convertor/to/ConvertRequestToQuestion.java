package com.example.leetdoce.convertor.to;

import com.example.leetdoce.dto.request.QuestionRequestForCreate;
import com.example.leetdoce.dto.request.QuestionRequestForUpdate;
import com.example.leetdoce.entity.QuestionEntity;

public class ConvertRequestToQuestion {

    private static final ThreadLocal<ConvertRequestToQuestion> instanceLocal = new ThreadLocal<>() ;

    public ConvertRequestToQuestion(){
    }

    public static ConvertRequestToQuestion getInstance(){
        ConvertRequestToQuestion question = instanceLocal.get();
        if (question == null){
            question = new ConvertRequestToQuestion();
            instanceLocal.set(question);
        }
        return question;
    }

    public QuestionEntity fromQuestionRequest(QuestionRequestForCreate questionRequestForCreate) {
            return QuestionEntity.builder()
                    .name(questionRequestForCreate.getName())
                    .definition(questionRequestForCreate.getDefinition())
                    .console(questionRequestForCreate.getConsole())
                    .level(questionRequestForCreate.getLevel())
                    .exampleEntityList(ConvertRequestToExample.makeRequestToQuestion(questionRequestForCreate.getExampleList()))
                    .like1(0)
                    .dislike(0)
                    .build();
    }

    public QuestionEntity fromQuestionRequest(QuestionRequestForUpdate questionRequestForUpdate) {
        return QuestionEntity.builder()
                .name(questionRequestForUpdate.getName())
                .definition(questionRequestForUpdate.getDefinition())
                .console(questionRequestForUpdate.getConsole())
                .level(questionRequestForUpdate.getLevel())
                .exampleEntityList(ConvertRequestToExample.makeRequestToQuestion(questionRequestForUpdate.getExampleList()))
                .build();
    }
}
