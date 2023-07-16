package com.example.leetdoce.convertor.from;

import com.example.leetdoce.dto.response.QuestionResponseWithoutTestCase;
import com.example.leetdoce.entity.QuestionEntity;
import com.example.leetdoce.entity.UserQuestionSource;
import com.example.leetdoce.simple_class.MarkQuestion;
import lombok.experimental.UtilityClass;

import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class ConvertResponseFromQuestion {

    public List<QuestionResponseWithoutTestCase> makeResponseFromQuestion(List<QuestionEntity> questionList){
        return questionList.stream().map(ConvertResponseFromQuestion::makeResponseFromQuestion).toList();
    }

    public List<QuestionResponseWithoutTestCase> makeResponseFromQuestion(List<QuestionEntity> questionList, List<UserQuestionSource> sourceList){
        List<QuestionResponseWithoutTestCase> list = new LinkedList<>();
        for (QuestionEntity question : questionList) {
            QuestionResponseWithoutTestCase ques = makeResponseFromQuestion(question);
            ques.setSolved(checkQuestionSolvedOrNot(question,sourceList));
            list.add(ques);
        }
        return list;
//        return questionList.stream().map(ConvertResponseFromQuestion::makeResponseFromQuestion).toList();
    }

    public QuestionResponseWithoutTestCase makeResponseFromQuestion(QuestionEntity question){
        return  QuestionResponseWithoutTestCase.builder()
                .id(question.getId())
                .name(question.getName())
                .dislike(question.getDislike())
                .like1(question.getLike1())
                .level(question.getLevel())
                .build();
    }

    public String checkQuestionSolvedOrNot(QuestionEntity question, List<UserQuestionSource> sourceList){
        for (UserQuestionSource source : sourceList) {
            if (question.getId()== source.getQuestionID() && source.getPositionQuestion()==1){
                return MarkQuestion.SUCCESS.getDescription();
            } else if (question.getId()== source.getQuestionID() && source.getPositionQuestion()==0) {
                return MarkQuestion.TRIED.getDescription();
            }
        }
            return MarkQuestion.NOT_CHANGED.getDescription();
    }
}
