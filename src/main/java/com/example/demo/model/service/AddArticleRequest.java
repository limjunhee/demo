package com.example.demo.model.service;
import lombok.*; // 어노테이션 자동 생성

//import java.lang.reflect.Member;
import com.example.demo.model.domain.Member;
// import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // getter, setter, toString, equals 등 자동 생성
@Builder
public class AddArticleRequest { //요청 데이터를 받는 DTO 객체
    private String title;
    private String content;
    private String user;
    private String newdate;
    private String count;
    private String likec;

    public Board toEntity(){ // Board 생성자를 통해 엔티티 객체 생성
        return Board.builder()
            .content(content)
            .title(title)
            .count(count)
            .likec(likec)
            .newdate(newdate)
            .user(user)
            .build();
    }
}