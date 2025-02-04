package org.kit.question_service.controller;


import org.kit.question_service.model.Question;
import org.kit.question_service.model.QuestionWrapper;
import org.kit.question_service.model.Response;
import org.kit.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> allQuestions() {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
          return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
           return questionService.addQuestion(question);
    }

    //CREATE
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String category, @RequestParam Integer numOfQuestions ) {
        return questionService.getQuestionsForQuiz(category, numOfQuestions);
    }

    //GET QUESTION(QUESTIION ID)
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId
    (@RequestBody List<Integer> questionIds) {
        return questionService.getQuestionsFromId(questionIds);
    }


    //GETSCORE
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }



}
