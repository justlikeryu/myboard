package com.example.myboard.member;

import com.example.myboard.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String username, String email, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));

        this.memberRepository.save(member);

        return member;
    }

    public Member getMember(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);

        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("not found");
        }
    }
}
