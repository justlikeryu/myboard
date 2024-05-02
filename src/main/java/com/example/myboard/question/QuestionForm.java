package com.example.myboard.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    //todo 아무 것도 입력하지 않으면 @NotEmpty에 의해 등록이 되지 않아야 하는데 등록이 되는 현상...
    @NotEmpty(message = "제목을 입력해 주세요.")//null, 빈 문자열 비허용
    @Size(max=200)//최대 길이는 200바이트
    private String subject;

    @NotEmpty(message = "내용을 입력해 주세요.")
    private String content;
}
