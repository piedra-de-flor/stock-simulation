package com.example.stocksimulation.service.membership;

import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.dto.membership.JwtToken;
import com.example.stocksimulation.dto.membership.MemberResponseDto;
import com.example.stocksimulation.dto.membership.MemberSignUpDto;
import com.example.stocksimulation.dto.membership.MemberUpdateDto;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.service.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto signUp(MemberSignUpDto signUpDto) {
        if (repository.findByEmail(signUpDto.email()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signUpDto.password());
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        Member savedMember = repository.save(signUpDto.toEntity(encodedPassword, roles));
        return MemberResponseDto.fromUser(savedMember);
    }

    @Transactional
    public JwtToken signIn(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public MemberUpdateDto update(MemberUpdateDto updateDto) {
        Member member = repository.findById(updateDto.memberId())
                .orElseThrow(NoSuchElementException::new);

        member.update(updateDto.nickName(), updateDto.password());
        return updateDto;
    }

    public Long delete(long memberId) {
        Member target = repository.findById(memberId)
                .orElseThrow(NoSuchElementException::new);

        repository.delete(target);
        return memberId;
    }
}
