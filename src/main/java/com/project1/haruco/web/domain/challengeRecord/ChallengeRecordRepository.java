package com.project1.haruco.web.domain.challengeRecord;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChallengeRecordRepository extends JpaRepository<Member, Long> {
    List<ChallengRecord> findAllByChallenge(Challenge challenge);
}
