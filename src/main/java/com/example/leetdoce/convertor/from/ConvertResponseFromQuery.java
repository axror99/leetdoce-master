package com.example.leetdoce.convertor.from;

import com.example.leetdoce.dto.response.QuestionResponseWithoutTestCase;
import com.example.leetdoce.entity.QueryEntity;
import com.example.leetdoce.entity.QuestionEntity;
import com.example.leetdoce.entity.UserQuestionSource;
import com.example.leetdoce.simple_class.MarkQuestion;
import lombok.experimental.UtilityClass;

import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class ConvertResponseFromQuery {

    public List<QuestionResponseWithoutTestCase> makeResponseFromQuery(List<QueryEntity> queryList){
        return queryList.stream().map(ConvertResponseFromQuery::makeResponseFromQuery).toList();
    }

    public List<QuestionResponseWithoutTestCase> makeResponseFromQuery(List<QueryEntity> queryList, List<UserQuestionSource> sourceList){
        List<QuestionResponseWithoutTestCase> list = new LinkedList<>();
        for (QueryEntity query : queryList) {
            QuestionResponseWithoutTestCase ques = makeResponseFromQuery(query);
            ques.setSolved(checkQuerySolvedOrNot(query,sourceList));
            list.add(ques);
        }
        return list;
//        return questionList.stream().map(ConvertResponseFromQuestion::makeResponseFromQuestion).toList();
    }

    public QuestionResponseWithoutTestCase makeResponseFromQuery(QueryEntity query){
        return  QuestionResponseWithoutTestCase.builder()
                .id(query.getId())
                .name(query.getName())
                .dislike(query.getDislike())
                .like1(query.getLike1())
                .level(query.getLevel())
                .build();
    }
    public String checkQuerySolvedOrNot(QueryEntity query, List<UserQuestionSource> sourceList){
        for (UserQuestionSource source : sourceList) {
            if (query.getId()== source.getQueryID() && source.getPositionQuestion()==1){
                return MarkQuestion.SUCCESS.getDescription();
            } else if (query.getId()== source.getQueryID() && source.getPositionQuestion()==0) {
                return MarkQuestion.TRIED.getDescription();
            }
        }
        return MarkQuestion.NOT_CHANGED.getDescription();
    }

}
