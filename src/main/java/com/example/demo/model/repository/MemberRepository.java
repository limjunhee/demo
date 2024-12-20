package com.example.demo.model.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    //Member findByEmail(String email);
    Optional<Member> findByEmail(String email); //이메일로 사용자 찾기
}