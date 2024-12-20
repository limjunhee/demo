package com.example.demo.model.domain;
import lombok.*; // 어노테이션 자동 생성

import jakarta.persistence.*; // 기존 javax 후속 버전

@Getter // setter는 없음(무분별한 변경 x)
@Entity // 아래 객체와 DB 테이블을 매핑. JPA가 관리
@Table(name = "Member") // 테이블 이름을 지정. 없는 경우 클래스이름으로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부 생성자 접근 방지

public class Member {
    @Id//기본 키 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 1씩 증가
    @Column(name = "id", updatable = false) // 기본 키값이 들어오는 칼럼, 수정 x
    private Long id;//기본 키 담긴 칼럼 이름
    
    @Column(name = "name", nullable = false) // 이름, null로 들어가면 안됨
    private String name = "";
    
    @Column(name = "email", unique = true, nullable = false) // 이메일 주소, unique 중복 x
    private String email = "";
    
    @Column(name = "password", nullable = false) // 비밀번호, null로 들어가면 안됨
    private String password = "";

    @Column(name = "age", nullable = false)//나이 담는 필드
    private String age = "";

    @Column(name = "mobile", nullable = false)//폰 번호 담는 필드
    private String mobile = "";

    @Column(name = "address", nullable = false)//집주소 담는 필드
    private String address = "";

    @Builder // 생성자에 빌더 패턴 적용(불변성)
    public Member(String name, String email, String password, String age, String mobile, String address){
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
    }
}
