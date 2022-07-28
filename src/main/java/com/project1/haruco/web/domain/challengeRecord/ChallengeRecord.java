package com.project1.haruco.web.domain.challengeRecord;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ChallengeRecord extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long challengeRecordId;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public ChallengeRecord(Challenge challenge, Member member) {
        this.challenge = challenge;
        this.member = member;
    }
}
