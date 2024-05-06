package com.example.myboard.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);

    Page<Question> findAll(Specification<Question> specification, Pageable pageable);

    @Query("SELECT "
            + "DISTINCT question "
            + "FROM Question question "
            + "LEFT OUTER JOIN Member authorQuestion ON question.author=authorQuestion "
            + "LEFT OUTER JOIN Answer answer ON answer.question=question "
            + "LEFT OUTER JOIN Member authorAnswer ON answer.author=authorAnswer "
            + "WHERE "
            + "   question.subject LIKE %:word% "
            + "   OR question.content LIKE %:word% "
            + "   OR authorQuestion.username LIKE %:word% "
            + "   OR answer.content LIKE %:word% "
            + "   OR authorAnswer.username LIKE %:word% ")
    Page<Question> findAllByWord(@Param("word") String word, Pageable pageable);
}
