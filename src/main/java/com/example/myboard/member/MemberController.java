package com.example.myboard.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(MemberForm memberForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup_form";
        }

        if(!memberForm.getPassword().equals(memberForm.getPassword2())){
            //bindingResult.rejectValue(필드명, 오류코드(임의로 정의), 오류메세지)
            bindingResult.rejectValue("password2", "passwordIncorrect", "비밀번호가 일치하지 않습니다");

            return "signup_form";
        }

        memberService.create(memberForm.getUsername(), memberForm.getEmail(), memberForm.getPassword());

        return "redirect:/";
    }
}
