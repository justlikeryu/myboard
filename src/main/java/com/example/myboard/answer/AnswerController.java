package com.example.myboard.answer;

import com.example.myboard.member.Member;
import com.example.myboard.member.MemberService;
import com.example.myboard.question.Question;
import com.example.myboard.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated")//로그인한 사용자만이 호출할 수 있다. 로그인하지 않은 사용자가 호출하면 로그인 페이지로 이동한다.
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        Member member = this.memberService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);

            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent(), member);

        return String.format("redirect:/question/detail/%s", id);
    }
}
