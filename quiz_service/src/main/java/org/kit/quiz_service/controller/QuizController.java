package org.kit.quiz_service.controller;



import org.kit.quiz_service.model.QuestionWrapper;
import org.kit.quiz_service.model.Quizdto;
import org.kit.quiz_service.model.Response;
import org.kit.quiz_service.service.QuizService;
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
    public ResponseEntity<String> createQuiz(@RequestBody Quizdto quizdto) {

        return quizService.createQuiz(quizdto.getCategory(),quizdto.getNoOfQuestions(),quizdto.getTitle());
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
