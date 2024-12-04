//게시판 게시글 작성, 수정, 삭제 등
package com.example.demo.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;//게시판 검색창에 사용되는 Page를 사용하기 위해 Spring Data JPA - org.springframework.data.domain의 Page 클래스 임포트

import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;
@Controller //컨트롤러 어노테이션 명시 파일을 다른 곳으로 옮겨도 자동으로 인식해 줌.

public class BlogController {

    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    BlogService blogService; // 리포지토리 선언
    
    @GetMapping("/board_view/{id}") // 게시판 링크 지정, 수정, 삭제 버튼 매핑
    public String board_view(Model model, @PathVariable Long id, HttpSession session) {
        Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("boards", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가

            // 현재 로그인한 사용자 이메일을 세션에서 가져와 모델에 추가
            String currentEmail = (String) session.getAttribute("email");
            model.addAttribute("currentEmail", currentEmail); // 현재 사용자 이메일 전달
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/board_error"; // 오류 처리 페이지로 연결
            }
            return "board_view"; // .HTML 연결
    }

    @DeleteMapping("/api/board_delete/{id}") //삭제버튼 매핑 얘가 위에 public class BlogRestController 안에 있어서 안됐음
    public String board_delete(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    @GetMapping("/board_edit/{id}") // 게시판 링크 지정
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("boards", list.get()); // 존재하면 Article 객체를 모델에 추가
            } else {
                // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
                return "error"; // 오류 처리 페이지로 연결
                }
                return "board_edit"; // .HTML 연결
        }

    @PutMapping("/api/board_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {  
        blogService.update(id, request); // 게시글 수정 처리
        return "redirect:/board_list"; // 글 수정 이후 .html 연결
    }

    
    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }
    

    // @PostMapping("/api/boards") // 글쓰기 게시판 저장(GUEST만 사용자로 반환함)
    //     public String addboards(@ModelAttribute AddArticleRequest request) {
    //     blogService.save(request);
    //     return "redirect:/board_list"; // .HTML 연결
    // }

    @PostMapping("/api/boards")//세션에서 이메일 가져오고, 이를 AddArticleRequest의 user 필드에 설정
    public String addboards(@ModelAttribute AddArticleRequest request, HttpSession session) {
        // 세션에서 이메일 가져오기
        String email = (String) session.getAttribute("email");
        if (email == null || email.isEmpty()) {
            email = "GUEST"; // 로그인하지 않은 경우 기본값
        }

        // 작성자 정보를 설정
        request.setUser(email);

        // 서비스 계층에서 저장 처리
        blogService.save(request);

        return "redirect:/board_list";
    }
    
    @GetMapping("/board_list") // 새로운 게시판 링크 지정 <<- 게시판의 게시글 개수를 3으로 제한하고 Get 방식으로 가져오고, 게시글 id 말고 글번호 가져오고, 세션에서 사용자 이메일 가져오는맵핑
    public String board_list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String keyword, HttpSession session) { //세션 전달

        String userId = (String) session.getAttribute("userId"); // 세션 아이디 존재 확인
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인

            if (userId == null) {
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉션
        }
        System.out.println("세션 userId: " + userId); // 서버 IDE 터미널에 세션 값 출력

        // 한 페이지의 게시글 수
        int pageSize = 3;
        PageRequest pageable = PageRequest.of(page, pageSize);
        
        Page<Board> list; // 게시글 목록을 담을 Page 객체

        // 키워드가 비어 있으면 전체 게시글 조회, 아니면 검색
        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable); // 전체 게시글
        } else {
            list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
        }

        // 시작 번호 계산 (현재 페이지의 첫 번째 게시글 번호)
        int startNum = (page * pageSize) + 1; // 페이지 번호와 페이지 크기를 사용해 첫 번째 게시글 번호 계산

        // 모델에 데이터 추가
        model.addAttribute("boards", list); // 게시글 목록
        model.addAttribute("totalPages", list.getTotalPages()); // 총 페이지 수
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("keyword", keyword); // 검색어
        model.addAttribute("startNum", startNum); // 시작 번호
        model.addAttribute("email", email); // 로그인한 사용자(이메일) 전달

        return "board_list"; // board_list.html로 데이터 전달
    }
    // @GetMapping("/board_list") // 새로운 게시판 링크 지정 <<- 게시판의 게시글 개수를 3으로 제한하고 Get 방식으로 가져오는 맵핑(비활성화)
    // public String board_list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String keyword) {
    //     PageRequest pageable = PageRequest.of(page, 3); // 한 페이지의 게시글 수
    //     Page<Board> list; // Page를 반환

    //     //키워드가 비어 있으면 전체 게시글을 조회하고 아니면 검색함
    //     if (keyword.isEmpty()) {
    //         list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
    //     } else {
    //         list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
    //     }
    //     model.addAttribute("boards", list); // 모델에 추가
    //     model.addAttribute("totalPages", list.getTotalPages()); // 페이지 크기
    //     model.addAttribute("currentPage", page); // 페이지 번호
    //     model.addAttribute("keyword", keyword); // 키워드
        
    //     return "board_list"; // .HTML 연결
    // }

        // @GetMapping("/board_list") // 새로운 게시판 링크 지정 <<- 게시판 전체 다 보여주는 Get 방식의 맵핑(비활성화)
        // public String board_list(Model model) {
        //     List<Board> list = blogService.findAll(); // 게시판 전체 리스트
        //     model.addAttribute("boards", list); // 모델에 추가
        //     return "board_list"; // .HTML 연결
        // }

    // @DeleteMapping("/api/article_delete/{id}") //삭제버튼 매핑 얘가 위에 public class BlogRestController 안에 있어서 안됐음
    // public String deleteArticle(@PathVariable Long id) {
    //     blogService.delete(id);
    //     return "redirect:/article_list";
    // }
    // @GetMapping("/article_list") // 게시판 링크 지정
    //     public String article_list(Model model) {
    //         List<Article> list = blogService.findAll(); // 게시판 리스트
    //         model.addAttribute("articles", list); // 모델에 추가
    //     return "article_list"; // .HTML 연결
    // }
    
    // @GetMapping("/article_edit/{id}") // 게시판 링크 지정
    // public String article_edit(Model model, @PathVariable Long id) {
    //     Optional<Article> list = blogService.findById(id); // 선택한 게시판 글
    //     if (list.isPresent()) {
    //         model.addAttribute("articles", list.get()); // 존재하면 Article 객체를 모델에 추가
    //         } else {
    //             // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
    //             return "error"; // 오류 처리 페이지로 연결
    //             }
    //             return "article_edit"; // .HTML 연결
    //     }

    // @PutMapping("/api/article_edit/{id}")
    // public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    //     blogService.update(id, request);
    //     return "redirect:/article_list"; // 글 수정 이후 .html 연결
    //     }
    

    // @PostMapping("/api/articles")
    // public String addArticle(@ModelAttribute AddArticleRequest request) {
    //     blogService.save(request); // 아티클 저장
    //     return "redirect:/article_list"; // article_list로 리다이렉트
    // }
    

}
