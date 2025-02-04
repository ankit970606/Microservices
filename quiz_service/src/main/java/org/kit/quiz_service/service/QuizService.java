package org.kit.quiz_service.service;


import org.kit.quiz_service.dao.QuizDao;
import org.kit.quiz_service.feign.QuizInterface;
import org.kit.quiz_service.model.QuestionWrapper;
import org.kit.quiz_service.model.Quiz;
import org.kit.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

//    @Autowired
//    Questiondao questiondao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int noOfQuestions, String title) {

   //     List<Integer> questions = //Call the generate URL --Rest Template --http://localhost:8080/question/generate
//        Quiz quiz = new Quiz();
//        quiz.setTitle(title);
//        quiz.setQuestions(questions);
//        quizDao.save(quiz);

        List<Integer> questions =quizInterface.getQuestionsForQuiz(category,noOfQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);



        return new ResponseEntity<>("Quiz created", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Quiz quiz =quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();

        ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<String> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);

        if (score.getBody() != null) {
            return new ResponseEntity<>("Your score is: " + score.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error calculating score", HttpStatus.BAD_REQUEST);
        }
    }

}
