package com.example.myboard.question;

import com.example.myboard.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //해당 속성만 번호가 1씩 증가
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT") //컬럼데이터의 유형 정의, 글자 수 제한 X
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) //질문 하나에 답변은 여러개
    private List<Answer> answerList;
}
