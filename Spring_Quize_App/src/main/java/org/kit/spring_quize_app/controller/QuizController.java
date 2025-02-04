package org.kit.spring_quize_app.controller;


import org.kit.spring_quize_app.model.QuestionWrapper;
import org.kit.spring_quize_app.model.Response;
import org.kit.spring_quize_app.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int noOfQuestions,@RequestParam String title) {

        return quizService.createQuiz(category,noOfQuestions,title);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer id) {
        return quizService.getQuizQuestion(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id,@RequestBody List<Response> responses ) {
        return quizService.calculateResult(id,responses);
    }

}
