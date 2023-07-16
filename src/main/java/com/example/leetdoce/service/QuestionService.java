package com.example.leetdoce.service;

import com.example.leetdoce.compilator.Java;
import com.example.leetdoce.compilator.Python;
import com.example.leetdoce.compilator.ResponseCompilator;
import com.example.leetdoce.convertor.from.ConvertResponseFromExample;
import com.example.leetdoce.dto.request.UserQuestion;
import com.example.leetdoce.dto.response.QuestionForUser;
import com.example.leetdoce.entity.*;
import com.example.leetdoce.exception.NotFoundCompilatorException;
import com.example.leetdoce.exception.NotFoundException;
import com.example.leetdoce.repository.ExampleRepository;
import com.example.leetdoce.repository.QuestionRepository;
import com.example.leetdoce.simple_class.Add_Package;
import com.example.leetdoce.simple_class.QuestionState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicService topicService;
    private final ExampleRepository exampleRepository;
    private final UserService userService;
    private final UserQuesSourceService userQuesSourceService;

    public void createQuestion(int id, QuestionEntity question) {
        TopicEntity oneTopic = topicService.getOneTopic(id);
        List<QuestionEntity> questionList = oneTopic.getQuestionList();
        QuestionEntity questionEntity1 = questionRepository.save(question);
        questionList.add(questionEntity1);
        oneTopic.setQuestionList(questionList);
        topicService.saveTopic(oneTopic);
    }

    public void updateQuestion(int id, int topicId, QuestionEntity question) {
        QuestionEntity questionEntity1 = questionRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} question was not found in database", question.getId())));

        if (question.getName() != null && !question.getName().equals("")) {
            questionEntity1.setName(question.getName());
        }
        if (question.getLevel() != null && !question.getLevel().equals("")) {
            questionEntity1.setLevel(question.getLevel());
        }
        if (question.getConsole() != null && !question.getConsole().isEmpty()) {
            Map<String, String> entityConsole = new HashMap<>(questionEntity1.getConsole());
            Map<String, String> console = new HashMap<>(question.getConsole());

            for (Map.Entry<String, String> entry : console.entrySet()) {
                entityConsole.put(entry.getKey(), entry.getValue());
            }
            questionEntity1.setConsole(entityConsole);
        }
        if (question.getDefinition() != null && !question.getDefinition().equals("")) {
            questionEntity1.setDefinition(question.getDefinition());
        }
        List<ExampleEntity> exampleEntity1ListDelete = null;
        if (question.getExampleEntityList() != null && !question.getExampleEntityList().isEmpty()) {
            List<ExampleEntity> exampleEntity1List = questionEntity1.getExampleEntityList();
            ;
            exampleEntity1ListDelete = new ArrayList<>(exampleEntity1List);
            exampleEntity1List.clear();
            exampleEntity1List.addAll(question.getExampleEntityList());
            questionEntity1.setExampleEntityList(exampleEntity1List);
        }
        if (topicId != 0) {
            TopicEntity oneTopic = topicService.getOneTopic(topicId);
            List<QuestionEntity> questionList = oneTopic.getQuestionList();
            questionList.remove(questionEntity1);
            questionList.add(questionEntity1);
            oneTopic.setQuestionList(questionList);
            topicService.saveTopic(oneTopic);
        } else {
            questionRepository.save(questionEntity1);
        }
        if (exampleEntity1ListDelete != null) {
            exampleRepository.deleteAll(exampleEntity1ListDelete);
        }
    }

    public QuestionEntity getOneQuestion(int id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} question was not found in DB", id)));
    }

    public QuestionForUser getUserQuestion(int id, int userId) {
        boolean stop = true;

        QuestionEntity oneQuestion = getOneQuestion(id);
        UserEntity user = userService.getUserById(userId);

        QuestionForUser questionForUser = QuestionForUser.builder()
                .id(oneQuestion.getId())
                .name(oneQuestion.getName())
                .definition(oneQuestion.getDefinition())
                .level(oneQuestion.getLevel())
                .like1(oneQuestion.getLike1())
                .dislike(oneQuestion.getDislike())
                .userInLikes(oneQuestion.getUserLikes())
                .userInDisLikes(oneQuestion.getUserDisLikes())
                .exampleList(ConvertResponseFromExample.makeResponseFromQuestion(oneQuestion.getExampleEntityList()))
                .build();

        List<UserQuestionSource> sourceList = user.getUserQuestionSourceList();
        for (UserQuestionSource questionSource : sourceList) {
            if (questionSource.getQuestionID() == oneQuestion.getId()) {
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
            questionForUser.setConsole(oneQuestion.getConsole());
            questionForUser.setSolved("No");
        }
        return questionForUser;
    }

    public ResponseCompilator submitQuestion(int id, UserQuestion userQuestion) throws Exception {
        Long l1 = System.currentTimeMillis();
        UserEntity currentUser = userService.getUserById(userQuestion.getUserId());
        QuestionEntity question = questionRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} question was not found in DB", id)));

        List<UserQuestionSource> sourceList = currentUser.getUserQuestionSourceList();
        UserQuestionSource questionSource = null;
        Map<String, String> writtenConsole;
        int index = 0;
        for (UserQuestionSource source : sourceList) {

            if (source.getQuestionID() == question.getId()) {
                questionSource = source;
                break;
            }
            index++;
        }
        if (questionSource == null) {
            questionSource = new UserQuestionSource();
            writtenConsole = new HashMap<>(question.getConsole());
        } else {
            writtenConsole = questionSource.getWrittenConsole();
        }
        writtenConsole.put(userQuestion.getLanguage(), userQuestion.getConsole());
        ResponseCompilator responseCompilator;
        if (userQuestion.getLanguage().equalsIgnoreCase("Java")) {
            responseCompilator = select_Java(question, userQuestion);

        } else if (userQuestion.getLanguage().equalsIgnoreCase("Python")) {
            responseCompilator = select_Python(question, userQuestion);
        } else {
            throw new NotFoundCompilatorException(MessageFormat.format("There is not {0} compilator in Server", userQuestion.getLanguage()));
        }

        if (responseCompilator.isPassed()) {
            questionSource.setRuntime(responseCompilator.getRuntime());
            questionSource.setPositionQuestion(QuestionState.SUCCESS.getState());
        }
        questionSource.setQuestionID(question.getId());
        questionSource.setWrittenConsole(writtenConsole);
        questionSource.setLevel(question.getLevel());
        UserQuestionSource savedSource = userQuesSourceService.saveQuesSource(questionSource);
        if (!sourceList.isEmpty() && index != sourceList.size()) {
            sourceList.remove(index);
        }
        sourceList.add(index, savedSource);
        currentUser.setUserQuestionSourceList(sourceList);
        userService.saveUser(currentUser);


        Long l2 = System.currentTimeMillis();
        long l3 = l2 - l1;
        System.out.println("===TIME=== :" + l3);
        return responseCompilator;
    }

    private ResponseCompilator select_Java(QuestionEntity question, UserQuestion userQuestion) throws Exception {
        return Java.run_Java(
                Add_Package.JAVA_PACKAGE.getPackages() + userQuestion.getConsole(),
                question.getTestCaseEntityList());
    }

    private ResponseCompilator select_Python(QuestionEntity question, UserQuestion userQuestion) {
        return Python.run_Python(
                userQuestion.getConsole(),
                question.getTestCaseEntityList());
    }

    public void deleteQuestion(int id, int topicId) {
        QuestionEntity question = questionRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} question was not found in DB", id)));
        topicService.deleteQuestionFromTopic(question, topicId);
    }

    public QuestionForUser resetQuestion(int id, int userId) {
        QuestionEntity oneQuestion = getOneQuestion(id);
        UserEntity user = userService.getUserById(userId);
        List<UserQuestionSource> sourceList = user.getUserQuestionSourceList();
        int index = 0;
        UserQuestionSource updateQ = null;
        for (UserQuestionSource sourceQ : sourceList) {
            if (sourceQ.getQuestionID() == oneQuestion.getId()) {
                sourceQ.setWrittenConsole(oneQuestion.getConsole());
                updateQ = userQuesSourceService.saveQuesSource(sourceQ);
                break;
            }
            index++;
        }
        if (updateQ != null && index!=sourceList.size()) {
            sourceList.remove(index);
            sourceList.add(index,updateQ);
        }
        user.setUserQuestionSourceList(sourceList);
        userService.saveUser(user);
        return getUserQuestion(id,userId);
    }

    public void buttonLike(int idQ, int userId) {
        QuestionEntity question = questionRepository.findById(idQ).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} question was not found in DB", idQ)));
        UserEntity user = userService.getUserById(userId);

        int dislike = question.getDislike();
        List<Integer> userDisLikes = question.getUserDisLikes();
        if (userDisLikes.contains(user.getId())) {
            userDisLikes.remove((Object)user.getId());
            dislike--;
        }
        int like = question.getLike1();
        List<Integer> userLikes = question.getUserLikes();
        boolean here = userLikes.contains(user.getId());
        if (here){
            userLikes.remove((Object)user.getId());
            like--;
        }else {
            userLikes.add(user.getId());
            like++;
        }
        question.setDislike(dislike);
        question.setLike1(like);
        question.setUserDisLikes(userDisLikes);
        question.setUserLikes(userLikes);
        questionRepository.save(question);
    }

    public void buttonDisLike(int idQ, int userId){
        QuestionEntity question = questionRepository.findById(idQ).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} question was not found in DB", idQ)));
        UserEntity user = userService.getUserById(userId);

        int like = question.getLike1();
        List<Integer> userLikes = question.getUserLikes();
        if (userLikes.contains(user.getId())){
            userLikes.remove((Object)user.getId());
            like--;
        }

        int dislike = question.getDislike();
        List<Integer> userDisLikes = question.getUserDisLikes();
        if (userDisLikes.contains(user.getId())){
            userDisLikes.remove((Object)user.getId());
            dislike--;
        }else {
            userDisLikes.add(user.getId());
            dislike++;
        }
        question.setDislike(dislike);
        question.setLike1(like);
        question.setUserDisLikes(userDisLikes);
        question.setUserLikes(userLikes);
        questionRepository.save(question);
    }

}
