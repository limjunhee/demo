package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class BlogGlobalError {

    // 문자열 변환 에러 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {
        // 에러 메시지를 모델에 추가
        model.addAttribute("error", "잘못된 요청입니다. 게시판 링크 매개변수가 올바르지 않습니다.");
        model.addAttribute("errorMessage", ex.getMessage()); // 디버깅용 메시지
        return "/error_page/board_error_conversion"; // 문자열 변환 오류 전용 에러 페이지 경로
    }
}
