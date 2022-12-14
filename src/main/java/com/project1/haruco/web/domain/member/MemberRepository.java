package com.project1.haruco.web.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    // Email로 멤버 조회
    Optional<Member> findByEmail(String email);
    // Nickname으로 멤버 조회
    Optional<Member> findByNickname(String nickname);

    // Email로 멤버 존재 여부 확인
    boolean existsByEmail(String email);

    // Nickname으로 멤버 존재 여부 확인
    boolean existsByNickname(String nickname);
}

