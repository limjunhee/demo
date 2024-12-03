package com.example.demo.model.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@Validated
@Transactional // 트랜잭션 처리(클래스 내 모든 메소드 대상)
@RequiredArgsConstructor // 생성자 자동 생성(부분)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private void validateDuplicateMember(@Valid AddMemberRequest request) {
        memberRepository.findByEmail(request.getEmail())
            .ifPresent(member -> {
                throw new IllegalStateException("이미 가입된 회원입니다."); // 예외 처리
            });
    }

    public Member saveMember(@Valid AddMemberRequest request) {
        validateDuplicateMember(request); // 이메일 체크

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword); // 암호화된 비밀번호 설정
        return memberRepository.save(request.toEntity());
    }

    public Member loginCheck(@Valid String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다.")); // 존재하지 않는 이메일 처리

        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); // 비밀번호 불일치 처리
        }

        return member; // 인증 성공 시 회원 객체 반환
    }
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}

// private final MemberRepository memberRepository;
//     private final PasswordEncoder passwordEncoder; // 스프링 버전 5 이후, 단방향 해싱 알고리즘 지원
    
//     private void validateDuplicateMember(@Valid AddMemberRequest request){
//         Member findMember = memberRepository.findByEmail(request.getEmail()); // 이메일 존재 유무
//         if(findMember != null){
//             throw new IllegalStateException("이미 가입된 회원입니다."); // 예외처리
//         }
//     }

//     public Member saveMember(@Valid AddMemberRequest request){
//         validateDuplicateMember(request); // 이메일 체크

//         String encodedPassword = passwordEncoder.encode(request.getPassword());
//         request.setPassword(encodedPassword); // 암호화된 비밀번호 설정
//         return memberRepository.save(request.toEntity());
//     }

//     public Member loginCheck(@Valid String email, String rawPassword) { //로그인시 체크함
//         Member member = memberRepository.findByEmail(email); // 이메일 조회
//         if (member == null) {
//             throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
//         }
//         if (!passwordEncoder.matches(rawPassword, member.getPassword())) { // 비밀번호 확인
//             throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//         }
//         return member; // 인증 성공 시 회원 객체 반환
//     }