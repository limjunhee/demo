package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.domain.Member;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;

import jakarta.validation.Valid;

@Controller //컨트롤러 어노테이션 명시 파일을 다른 곳으로 옮겨도 자동으로 인식해 줌.

public class MemberController {

    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    MemberService memberService; // 리포지토리 선언

    @GetMapping("/join_new") // 회원 가입 페이지 연결
    public String join_new() {
        return "join_new"; // .HTML 연결
    }
    
    @PostMapping("/api/members")
public String addmembers(@Valid @ModelAttribute AddMemberRequest request, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        // 오류 메시지를 모델에 추가하여 join_new.html에서 출력할 수 있게 함.
        model.addAttribute("errors", bindingResult.getAllErrors());
        return "join_new";  // 오류가 있을 경우 회원가입 페이지로 돌아갑니다.
    }
    memberService.saveMember(request);
    return "join_end";  // 회원가입 성공 시 이동할 페이지
}

    
    @GetMapping("/member_login") // 로그인 페이지 연결
    public String member_login() {
    return "login"; // .HTML 연결
    }
    
    @PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
    public String checkMembers(@Valid @ModelAttribute AddMemberRequest request, Model model) {
        try {
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword()); // 패스워드 반환
            model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
            return "redirect:/board_list"; // 로그인 성공 후 이동할 페이지
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
        }
    }
}
