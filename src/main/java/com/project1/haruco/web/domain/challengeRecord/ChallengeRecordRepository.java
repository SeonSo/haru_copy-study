package com.project1.haruco.web.domain.challengeRecord;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeRecordRepository extends JpaRepository<ChallengeRecord, Long> {

    ChallengeRecord findChallengeAndMemberAndChallengeRecordStatusTrue(Challenge challenge, Member member);
    List<ChallengeRecord> findAllByChallengeAndChallengeRecordStatusTrue(Challenge challenge);

    @Modifying(clearAutomatically = true)
    @Query("update ChallengeRecord c set c.challengeRecordStatus = false where c.member.memberId in :kickChallenge and c.challenge.challengeId in :kickChallenge")
    int kickMemberOnChallenge(List<Long> kickMember, List<Long> kickChallenge);

    @Query("select CASE when count(c.challengeRecordId) > 0 then true else false end " +
            "from ChallengeRecord c " +
            "where c.challengeRecordStatus = true " +
            "and c.member = :member " +
            "and c.challenge.challengeProgress in (:progress, :expected) ")
    boolean existsByChallengeIdAndMember(Long challengeId, Member member, Long progress, Long expected);

    void deleteByMember(Member member);
}
