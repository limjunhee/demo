package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.model.domain.Article;
import com.example.demo.model.service.BlogService;
@Controller //컨트롤러 어노테이션 명시 파일을 다른 곳으로 옮겨도 자동으로 인식해 줌.

public class BlogController {
    @GetMapping("/article_list") // 게시판 링크 지정
        public String article_list(Model model) {
        List<Article> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("articles", list); // 모델에 추가
        return "article_list"; // .HTML 연결
    }
}
