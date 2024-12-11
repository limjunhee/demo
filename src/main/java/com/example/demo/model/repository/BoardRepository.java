package com.example.demo.model.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;//게시판 검색창에 사용되는 Page를 사용하기 위해 Spring Data JPA - org.springframework.data.domain의 Page 클래스 임포트
import org.springframework.data.domain.Pageable;//게시판 검색창에 사용되는 Pageable을 사용하기 위해 Spring Data JPA - org.springframework.data.domain의 Pageable 클래스 임포트
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Board;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable); //대소문자 구분 없이 제목으로 Board에서 해당하는 결과(게시물) 찾기
    // List<Article> findAll();
}