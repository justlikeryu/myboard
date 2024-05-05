package com.example.myboard.answer;

import com.example.myboard.member.Member;
import com.example.myboard.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne //질문 하나에 답변은 여러개
    private Question question;

    @ManyToOne//사용자 한 명에 답변은 여러개
    private Member author;
}
