package com.project1.haruco.web.domain.challengeRecord;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@ToString(exclude = {"member","challenge"})
@Table(indexes = {@Index(name = "idx_record_status", columnList = "challenge_record_status")})
public class ChallengeRecord extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long challengeRecordId;

    @Column
    private boolean challengePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // ChallengeProgress 가 1 or 2이면 true, 3이면 false
    @Column(name = "challenge_record_status", nullable = false)
    private boolean challengeRecordStatus;

    @Builder
    public ChallengeRecord(Challenge challenge, Member member) {
        this.challengePoint = false;
        this.challenge = challenge;
        this.member = member;
        this.challengeRecordStatus = true;
    }

    public static ChallengeRecord createChallengeRecord(Challenge challenge, Member member){
        return ChallengeRecord.builder()
                .challenge(challenge)
                .member(member)
                .build();
    }

    public void setStatusFalse() {
        this.challengeRecordStatus = false;
    }
}
