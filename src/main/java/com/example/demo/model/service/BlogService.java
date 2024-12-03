package com.example.demo.model.service;
// import java.util.List;

import java.util.List;
import java.util.Optional;

// import java.util.Optional;
// import org.springframework.beans.factory.annotation.Autowired;
// import com.example.demo.model.domain.Article;
// import com.example.demo.model.repository.BlogRepository;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;//게시판 검색창에 사용되는 Page를 사용하기 위해 Spring Data JPA - org.springframework.data.domain의 Page 클래스 임포트
import org.springframework.data.domain.Pageable;//게시판 검색창에 사용되는 Pageable을 사용하기 위해 Spring Data JPA - org.springframework.data.domain의 Pageable 클래스 임포트
@Service

@RequiredArgsConstructor // 생성자 자동 생성(부분)

public class BlogService {
    // @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    // private final BlogRepository blogRepository; // 리포지토리 선언
    // public List<Article> findAll() { // 게시판 전체 목록 조회
    //     return blogRepository.findAll();
    // }
    // public Optional<Article> findById(Long id) { // 게시판 특정 글 조회
    //     return blogRepository.findById(id);
    // }
    // public void update(Long id, AddArticleRequest request) {
    //     Optional<Article> optionalArticle = blogRepository.findById(id); // 단일 글 조회
    //     optionalArticle.ifPresent(article -> { // 값이 있으면
    //         article.update(request.getTitle(), request.getContent()); // 값을 수정
    //         blogRepository.save(article); // Article 객체에 저장
    //         });
    //     }

    // public Article save(AddArticleRequest request){
    //     // DTO가 없는 경우 이곳에 직접 구현 가능
    //     // public ResponseEntity<Article> addArticle(@RequestParam String title, @RequestParam String content) {
    //     // Article article = Article.builder()
    //     // .title(title)
    //     // .content(content)
    //     // .build();
    //     return blogRepository.save(request.toEntity());
    //     }
    
    // public void delete(Long id) {
    //     blogRepository.deleteById(id);
    //     }

    private final BoardRepository blogRepository; // 리포지토리 선언

    public List<Board> findAll() { // 게시판 전체 목록 조회
        return blogRepository.findAll();
    }
    public Optional<Board> findById(Long id) { // 게시판 특정 글 조회
        return blogRepository.findById(id);
    }

    public void update(Long id, AddArticleRequest request) {
        Optional<Board> optionalArticle = blogRepository.findById(id); // 단일 글 조회
        optionalArticle.ifPresent(article -> { // 값이 있으면
            article.update(request.getTitle(), request.getContent(), article.getUser(), article.getNewdate(), article.getCount(), article.getLikec()); // 값을 수정
            blogRepository.save(article); // Article 객체에 저장
            });
    }

    public Board save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    public Page<Board> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return blogRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }
    public void saveMember(AddMemberRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveMember'");
    } // LIKE 검색 제공(대소문자 무시)
}

