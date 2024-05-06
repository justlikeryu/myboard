package com.example.myboard.question;

import com.example.myboard.answer.Answer;
import com.example.myboard.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    private LocalDateTime modifyDate;

    @ManyToMany//하나의 질문에 여러 사람이 추천할 수 있고 한 사람은 여러 질문을 추천할 수 있다
    Set<Member> recommender;//속성값이 중복되지 않도록 set 자료형 사용

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) //질문 하나에 답변은 여러개
    private List<Answer> answerList;

    @ManyToOne//사용자 한 명에 질문은 여러개
    private Member author;
}
