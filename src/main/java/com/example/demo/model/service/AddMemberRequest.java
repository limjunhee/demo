package com.example.demo.model.service;
import lombok.*; // 어노테이션 자동 생성

import com.example.demo.model.domain.Member;

import jakarta.validation.constraints.*;
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // getter, setter, toString, equals 등 자동 생성
@Builder
public class AddMemberRequest {
    
    @NotEmpty(message = "이름을 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]*$", message = "이름에 공백이나 특수문자를 사용할 수 없습니다.") // 공백 및 특수문자 금지
    private String name;

    @NotEmpty(message = "이메일을 입력하세요.")
    @Email(message = "유효한 이메일 주소를 입력하세요.") // 이메일 형식 검증
    private String email;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$",
        message = "비밀번호는 8글자 이상이어야 하며, 대소문자를 포함해야 합니다."
    ) // 8자 이상, 대소문자 포함
    private String password;

    @NotEmpty(message = "나이를 입력하세요.")
    @Pattern(regexp = "^(1[9-9]|[2-8][0-9]|90)$", message = "나이는 19세 이상 90세 이하로 입력해야 합니다.") // 나이 범위 제한
    private String age;

    private String mobile; // 별도의 규칙 없음

    private String address; // 공백 허용, 제한 없음
    public Member toEntity(){ // Member 생성자를 통해 객체 생성
    return Member.builder()
        .name(name)
        .email(email)
        .password(password)
        .age(age)
        .mobile(mobile)
        .address(address)
        .build();
    }

}
