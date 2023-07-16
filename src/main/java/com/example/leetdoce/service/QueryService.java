package com.example.leetdoce.service;

import com.example.leetdoce.compilator.Postgres;
import com.example.leetdoce.compilator.ResponseCompilator;
import com.example.leetdoce.convertor.from.ConvertResponseFromExample;
import com.example.leetdoce.convertor.to.ConvertRequestToExample;
import com.example.leetdoce.dto.request.QueryRequestForUpdate;
import com.example.leetdoce.dto.request.UserQuestion;
import com.example.leetdoce.dto.response.QuestionForUser;
import com.example.leetdoce.entity.*;
import com.example.leetdoce.exception.NotFoundCompilatorException;
import com.example.leetdoce.exception.NotFoundException;
import com.example.leetdoce.repository.QueryRepository;
import com.example.leetdoce.simple_class.QuestionState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepository queryRepository;
    private final TopicService topicService;
    private final JDBC_Service jdbcService;
    private final UserService userService;
    private final UserQuesSourceService userQuesSourceService;


    public void createTableUsingQuery(int id, QueryEntity queryEntity) {
        TopicEntity oneTopic = topicService.getOneTopic(id);
        List<QueryEntity> queryList = oneTopic.getQueryList();
        QueryEntity query = queryRepository.save(queryEntity);
        queryList.add(query);
        oneTopic.setQueryList(queryList);
        topicService.saveTopic(oneTopic);
        jdbcService.createTableViaSchema(query.getSchemaName(),query.getDdl(),query.getDml());
    }

    public void updateQuery(int id, QueryRequestForUpdate queryUpdate) {
        QueryEntity dbQuery = queryRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("id = {0} not found in database", id)));
        if (queryUpdate.getName()!= null && ! queryUpdate.getName().equals("")){
            dbQuery.setName(queryUpdate.getName());
        }
        if (queryUpdate.getCorrectAnswer()!=null && !queryUpdate.getCorrectAnswer().equals("")){
            dbQuery.setCorrectAnswer(queryUpdate.getCorrectAnswer());
        }
        if (queryUpdate.getDdl()!=null && !queryUpdate.getDdl().equals("")){
            dbQuery.setDdl(queryUpdate.getDdl());
        }
        if (queryUpdate.getDml()!=null && !queryUpdate.getDml().equals("")){
            dbQuery.setDml(queryUpdate.getDml());
        }
        if (queryUpdate.getDefinition()!=null && !queryUpdate.getDefinition().equals("")){
            dbQuery.setDefinition(queryUpdate.getDefinition());
        }
        if (queryUpdate.getLevel()!=null && !queryUpdate.getLevel().equals("")){
            dbQuery.setLevel(queryUpdate.getLevel());
        }
        if (queryUpdate.getConsole()!=null && !queryUpdate.getConsole().isEmpty()){
            dbQuery.setConsole(queryUpdate.getConsole());
        }
        if (queryUpdate.getExampleList()!=null && !queryUpdate.getExampleList().isEmpty()){
            List<ExampleEntity> exampleEntityList = dbQuery.getExampleEntityList();
            exampleEntityList.clear();
            exampleEntityList.addAll(ConvertRequestToExample.makeRequestToQuestion(queryUpdate.getExampleList()));
        }
        if (queryUpdate.getTableDefinition()!=null && !queryUpdate.getTableDefinition().isEmpty()){

            dbQuery.setDefinitionTables(queryUpdate.getTableDefinition());
        }
        if (queryUpdate.getSchemaName()!=null && !queryUpdate.getSchemaName().equals("")){
            dbQuery.setSchemaName(queryUpdate.getSchemaName());
        }
        queryRepository.save(dbQuery);
        jdbcService.deleteTableViaSchema(dbQuery.getSchemaName());
        jdbcService.createTableViaSchema(dbQuery.getSchemaName(),dbQuery.getDdl(),queryUpdate.getDml());
    }

    public void deleteQuery(int topicId, int id) {
        TopicEntity oneTopic = topicService.getOneTopic(topicId);
        List<QueryEntity> queryList = oneTopic.getQueryList();
        QueryEntity queryQuestion = getOneQueryQuestion(id);
        queryList.remove(queryQuestion);
        topicService.saveTopic(oneTopic);
        queryRepository.deleteById(id);
        jdbcService.deleteTableViaSchema(queryQuestion.getSchemaName());
    }

    public List<String> getAllSchemasInDB() {
        return jdbcService.getSchemasList();
    }

    public ResponseCompilator submitQuery(int id, UserQuestion userQuestion) {
        UserEntity currentUser = userService.getUserById(userQuestion.getUserId());
        QueryEntity queryEntity = queryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} query was not found in DB", id)));

        List<UserQuestionSource> sourceList = currentUser.getUserQuestionSourceList();
        UserQuestionSource questionSource = null;
        Map<String, String> writtenConsole;
        int index = 0;
        for (UserQuestionSource source : sourceList) {

            if (source.getQueryID() == queryEntity.getId()) {
                questionSource = source;
                break;
            }
            index++;
        }
        if (questionSource == null) {
            questionSource = new UserQuestionSource();
            writtenConsole = new HashMap<>(queryEntity.getConsole());
        } else {
            writtenConsole = questionSource.getWrittenConsole();
        }
        writtenConsole.put(userQuestion.getLanguage(), userQuestion.getConsole());
        ResponseCompilator responseCompilator ;

        if (userQuestion.getLanguage().equals("postgres")){
            responseCompilator =Postgres.run_Postgres(userQuestion.getConsole(),queryEntity);
        }else {
            throw new NotFoundCompilatorException(MessageFormat.format("There is not {0} compilator in Server", userQuestion.getLanguage()));
        }
        if (responseCompilator.isPassed()) {
            questionSource.setRuntime(responseCompilator.getRuntime());
            questionSource.setPositionQuestion(QuestionState.SUCCESS.getState());
        }
        questionSource.setQueryID(queryEntity.getId());
        questionSource.setWrittenConsole(writtenConsole);
        questionSource.setLevel(queryEntity.getLevel());
        UserQuestionSource savedSource = userQuesSourceService.saveQuesSource(questionSource);
        if (!sourceList.isEmpty() && index != sourceList.size()) {
            sourceList.remove(index);
        }
        sourceList.add(index, savedSource);
        currentUser.setUserQuestionSourceList(sourceList);
        userService.saveUser(currentUser);

        return responseCompilator;
    }

    public QueryEntity getOneQueryQuestion(int id){
        return queryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} query was not found in DB", id)));
    }
    public QuestionForUser getUserQueryQuestion(int id, int userId) {
        boolean stop = true;

        QueryEntity oneQueryQuestion = getOneQueryQuestion(id);
        UserEntity user = userService.getUserById(userId);

        QuestionForUser questionForUser = QuestionForUser.builder()
                .id(oneQueryQuestion.getId())
                .name(oneQueryQuestion.getName())
                .definition(oneQueryQuestion.getDefinition())
                .level(oneQueryQuestion.getLevel())
                .like1(oneQueryQuestion.getLike1())
                .dislike(oneQueryQuestion.getDislike())
                .userInLikes(oneQueryQuestion.getUserLikes())
                .userInDisLikes(oneQueryQuestion.getUserDisLikes())
                .exampleList(ConvertResponseFromExample.makeResponseFromQuestion(oneQueryQuestion.getExampleEntityList()))
                .build();

        List<UserQuestionSource> sourceList = user.getUserQuestionSourceList();
        for (UserQuestionSource questionSource : sourceList) {
            if (questionSource.getQueryID() == oneQueryQuestion.getId()) {
                questionForUser.setConsole(questionSource.getWrittenConsole());
                if (questionSource.getPositionQuestion() == QuestionState.SUCCESS.getState()) {
                    questionForUser.setSolved("Yes");
                } else {
                    questionForUser.setSolved("No");
                }
                stop = false;
                break;
            }
        }
        if (stop) {
            questionForUser.setConsole(oneQueryQuestion.getConsole());
            questionForUser.setSolved("No");
        }
        return questionForUser;
    }
}
