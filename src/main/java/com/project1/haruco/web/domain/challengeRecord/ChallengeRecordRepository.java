package com.project1.haruco.web.domain.challengeRecord;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRecordRepository extends JpaRepository<ChallengeRecord, Long> {
    List<ChallengeRecord> findAllByChallenge(Challenge challenge);
    List<ChallengeRecord> findAll();
    Page<ChallengeRecord> findAll(Pageable pageable);
    void deleteAllByChallenge(Challenge challenge);
    List<ChallengeRecord> findAllByMember(Member member);
}
