package com.example.myboard;

import com.example.myboard.answer.Answer;
import com.example.myboard.answer.AnswerRepository;
import com.example.myboard.question.Question;
import com.example.myboard.question.QuestionRepository;
import com.example.myboard.question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Configuration
class MyboardApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("addQuestion: 질문하기")
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("제목1");
        q1.setContent("내용1");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("제목2");
        q2.setContent("내용2");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);
    }

    @Test
    @DisplayName("findAllQuestion: 전체 질문 조회")
    void selectAllQuestion() {
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("제목1", q.getSubject());
    }

    @Test
    @DisplayName("findQuestion: 특정 질문 조회")
    void selectQuestion() {
        Optional<Question> question = this.questionRepository.findById(1);

        if (question.isPresent()) {
            Question q = question.get();
            assertEquals("제목1", q.getSubject());
        }
    }

    @Test
    @DisplayName("findQuestionBySubject: 제목으로 질문 조회")
    void selectQuestionBySubject() {
        Question question = this.questionRepository.findBySubject("제목1");
        assertEquals(1, question.getId());
    }

    @Test
    @DisplayName("findBySubjectAndContet: 제목과 내용으로 질문 조회")
    void selectQuestionBySubjectAndContent() {
        Question question = this.questionRepository.findBySubjectAndContent("제목1", "내용1");
        assertEquals(1, question.getId());
    }

    @Test
    @DisplayName("findBySubjectLike: 특정 문자열로 질문 조회")
    void selectBySubjectLike() {
        List<Question> questions = this.questionRepository.findBySubjectLike("제목%");
        Question question = questions.get(0);
        assertEquals("제목1", question.getSubject());
    }

    @Test
    @DisplayName("editQuestion: 질문 수정하기")
    void updateQuestion() {
        Optional<Question> question = this.questionRepository.findById(1);
        assertTrue(question.isPresent()); //값이 참인지 확인

        Question q = question.get();
        q.setSubject("수정한 제목");
        this.questionRepository.save(q);
    }

    @Test
    @DisplayName("deleteQuestion: 질문 삭제하기")
    void deleteQuestion() {
        Optional<Question> question = this.questionRepository.findById(1);
        assertTrue(question.isPresent());

        Question q = question.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

    @Test
    @DisplayName("addAnswer: 답변하기")
    void addAnswer() {
        Optional<Question> question = this.questionRepository.findById(2);
        assertTrue(question.isPresent());
        Question q = question.get();

        Answer answer = new Answer();
        answer.setContent("답변2");
        answer.setQuestion(q);
        answer.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    @Test
    @DisplayName("findAnswer: 답변 조회")
    void selectAnswer() {
        Optional<Answer> answer = this.answerRepository.findById(1);
        assertTrue(answer.isPresent());

        Answer a = answer.get();
        assertEquals(2, a.getQuestion().getId());
    }

    @Test
    @Transactional
    @DisplayName("findAnswerByQuestion: 질문으로 답변 조회")
    void selectByAnswer() {
        Optional<Question> question = this.questionRepository.findById(2);
        assertTrue(question.isPresent());
        Question q = question.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("답변2", answerList.get(0).getContent());
    }

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("createTestCase: 테스트 케이스 추가")
    void createTestCase() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터:[%03d]", i);
            String content = "내용무";

            this.questionService.create(subject, content);
        }
    }
}
