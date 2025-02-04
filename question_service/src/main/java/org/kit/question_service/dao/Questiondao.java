package org.kit.question_service.dao;


import org.kit.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Questiondao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM question q WHERE  q.category=:category ORDER BY RANDOM() LIMIT :noOfQuestions",nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(String category, int noOfQuestions);

}
