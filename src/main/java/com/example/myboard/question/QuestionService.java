package com.example.myboard.question;

import com.example.myboard.DataNotFoundException;
import com.example.myboard.answer.Answer;
import com.example.myboard.member.Member;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page, String word) {//페이지 번호를 입력 받으면 해당 페이지의 객체 리턴
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));//정렬 조건 추가
        Specification<Question> specification = search(word);

        //최신순으로 조회하기 위해 Sort 객체 전달
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));//page: 조회할 페이지 번호, 10: 한 페이지에 보여줄 게시물 개수

        return this.questionRepository.findAll(specification, pageable);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("Not Found");
        }
    }

    public void create(String subject, String content, Member author) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(author);

        this.questionRepository.save(question);
    }

    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());

        this.questionRepository.save(question);
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void recommend(Question question, Member member){
        question.getRecommender().add(member);

        this.questionRepository.save(question);
    }

    private Specification<Question> search(String word){
        return new Specification<>(){
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);//중복 제거

                Join<Question, Member> writerQuestion= root.join("author", JoinType.LEFT);
                Join<Question, Answer> answer = root.join("answerList", JoinType.LEFT);
                Join<Answer, Member> writerAnswer = answer.join("author", JoinType.LEFT);

                return criteriaBuilder.or(criteriaBuilder.like(root.get("subject"), "%" + word + "%"), // 제목
                        criteriaBuilder.like(root.get("content"), "%" + word + "%"), // 내용
                        criteriaBuilder.like(writerQuestion.get("username"), "%" + word + "%"), // 질문 작성자
                        criteriaBuilder.like(answer.get("content"), "%" + word + "%"), // 답변 내용
                        criteriaBuilder.like(writerAnswer.get("username"), "%" + word + "%")); // 답변 작성자
            }
        };
    }
}
