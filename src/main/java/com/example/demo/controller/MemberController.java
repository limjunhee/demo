//회원가입, 로그인 등
package com.example.demo.controller;

import java.util.UUID;

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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public String checkMembers(@Valid @ModelAttribute AddMemberRequest request, Model model, HttpServletRequest request2, HttpServletResponse response) {
          
        try {
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
            if (session != null) {
            session.invalidate(); // 기존 세션 무효화
            Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID 초기화
            cookie.setPath("/"); // 쿠키 경로
            cookie.setMaxAge(0); // 쿠키 삭제(0으로 설정)
            response.addCookie(cookie); // 응답으로 쿠키 전달
            }
            session = request2.getSession(true); // 새로운 세션 생성
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword()); // 패스워드 반환
            String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID로 세션 생성
            String email = request.getEmail(); // 이메일 얻기
            session.setAttribute("userId", sessionId); // 아이디 이름 설정
            session.setAttribute("email", email); // 이메일 설정

            model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
            return "redirect:/board_list"; // 로그인 성공 후 이동할 페이지
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
        }
    }

    @GetMapping("/api/logout") // 로그아웃 버튼 동작
    public String member_logout(Model model, HttpServletRequest request2, HttpServletResponse response) {
        try {
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
            session.invalidate(); // 기존 세션 무효화(세션 정보를 모두 제거)
            Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID-> 세션의 디폴트 네임으로 초기화
            cookie.setPath("/"); // 쿠키의 경로 비워둠
            cookie.setMaxAge(0); // 쿠키의 유효 시간 0으로 설정 = 즉시 삭제
            response.addCookie(cookie);
            session = request2.getSession(true); // 새로운 세션 생성
            System.out.println("세션 userId: " + session.getAttribute("userId" )); // 초기화 후 IDE 터미널에 세션 값 출력
            return "login"; // 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
        }
    }
}
