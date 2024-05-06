package com.example.myboard.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목을 입력해 주세요.")//null, 빈 문자열 비허용
    @Size(max=200)//최대 길이는 200바이트
    private String subject;

    @NotEmpty(message = "내용을 입력해 주세요.")
    private String content;
}
