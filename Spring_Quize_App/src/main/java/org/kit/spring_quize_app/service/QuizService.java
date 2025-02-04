package org.kit.spring_quize_app.service;

import org.kit.spring_quize_app.dao.Questiondao;
import org.kit.spring_quize_app.dao.QuizDao;
import org.kit.spring_quize_app.model.Question;
import org.kit.spring_quize_app.model.QuestionWrapper;
import org.kit.spring_quize_app.model.Quiz;
import org.kit.spring_quize_app.model.Response;
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
    @Autowired
    Questiondao questiondao;

    public ResponseEntity<String> createQuiz(String category, int noOfQuestions, String title) {
        List<Question> questions = questiondao.findRandomQuestionByCategory(category, noOfQuestions);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz created", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Optional<Quiz> quiz =quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question question : questionsFromDB) {
            QuestionWrapper qw=new QuestionWrapper(question.getId(),question.getQuestionTitle(),question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<String> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz =quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        int right=0;
        int i =0;
        for (Response response : responses) {
            if (response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right+"",HttpStatus.OK);
    }
}
