package com.project1.haruco.web.domain.challenge;

import com.project1.haruco.web.domain.member.Member;
import jdk.jfr.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Optional<Challenge> findByChallengeIdAndMember(Long challengeId, Member member);
    List<Challenge> findAllByMember(Member member);

    Page<Challenge> findAllByCategoryNameOrderByModifiedAtDesc(CategoryName categoryName, Pageable pageable);

    @Query("select c from Challenge c where c.challengeId =:challengeId and c.challengeStatus = true")
    Challenge findChallengeStatusTrue(Long challengeId);
}
