package com.example.myboard.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "아이디를 작성해 주세요.")
    private String username;

    @NotEmpty(message = "비밀번호를 작성해 주세요.")
    private String password;

    @NotEmpty(message = "비밀번호를 확인해 주세요.")
    private String password2;

    @NotEmpty(message = "이메일을 작성해 주세요.")
    @Email
    private String email;
}
