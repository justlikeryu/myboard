package com.example.myboard.answer;

import com.example.myboard.DataNotFoundException;
import com.example.myboard.member.Member;
import com.example.myboard.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content, Member author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);

        this.answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());

        this.answerRepository.save(answer);
    }

    public void delete(Answer answer){
        this.answerRepository.delete(answer);
    }

    public void recommend(Answer answer, Member member){
        answer.getRecommender().add(member);

        this.answerRepository.save(answer);
    }
}
