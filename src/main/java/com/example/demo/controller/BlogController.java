package com.example.demo.controller;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
@Controller //컨트롤러 어노테이션 명시 파일을 다른 곳으로 옮겨도 자동으로 인식해 줌.

public class BlogController {

    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    BlogService blogService; // 리포지토리 선언
    @GetMapping("/article_list") // 게시판 링크 지정
        public String article_list(Model model) {
            List<Article> list = blogService.findAll(); // 게시판 리스트
            model.addAttribute("articles", list); // 모델에 추가
        return "article_list"; // .HTML 연결
    }
    @GetMapping("/article_edit/{id}") // 게시판 링크 지정
    
    public String article_edit(Model model, @PathVariable Long id) {
        Optional<Article> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
            } else {
                // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
                return "error"; // 오류 처리 페이지로 연결
                }
                return "article_edit"; // .HTML 연결
        }

    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list"; // 글 수정 이후 .html 연결
        }
    
    public class BlogRestController {
        private BlogService blogService;
        // 생성자 주입
        public BlogRestController(BlogService blogService) {
            this.blogService = blogService;
        }
    @PostMapping("/api/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request); // 아티클 저장
        return "redirect:/article_list"; // article_list로 리다이렉트
    }
    }
    @DeleteMapping("/api/article_delete/{id}") //삭제버튼 매핑
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }
}
