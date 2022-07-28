package com.project1.haruco.web.domain.challenge;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.dto.request.challenge.ChallengeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Entity
@Getter
@Table(indexes = {@Index(name = "idx_status_progress", columnList = "challenge_status, challenge_progress"),
@Index(name = "idx_status", columnList = "challenge_status")})
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(nullable = false)
    private String challengeTitle;

    @Column(columnDefinition = "TEXT")
    private String challengeContent;

    @Column(name = "category_name", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CategoryName categoryName;

    @Column(nullable = false)
    private String challengePassword;

    @Column(nullable = false)
    private LocalDateTime challengeStartDate;

    @Column(nullable = false)
    private LocalDateTime challengeEndDate;

    //삭제
    @Column(name = "challenge_status", nullable = false)
    private boolean challengeStatus;

    @Column(name = "challenge_progress", nullable = false)
    private Long challengeProgress;

    @Column
    private String challengeImgUrl;

    @Column(length = 3000)
    private String challengeGood;

    @Column(length = 3000)
    private String challengeBad;

    @Column
    private String challengeHoliday;

    @Column
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    public Challenge(String challengeTitle, String challengeContent,
                     CategoryName categoryName, String challengePassword,
                     LocalDateTime challengeStartDate, LocalDateTime challengeEndDate,
                     String challengeImgUrl, String challengeGood,
                     String challengeBad, String challengeHoliday, Member member){
        this.challengeTitle = challengeTitle;
        this.challengeContent = challengeContent;
        this.categoryName = categoryName;
        this.challengePassword = challengePassword;
        this.challengeStartDate =   challengeStartDate;
        this.challengeEndDate = challengeEndDate;
        this.challengeImgUrl = challengeImgUrl;
        this.challengeGood = challengeGood;
        this.challengeBad = challengeBad;
        this.challengeHoliday = challengeHoliday;
        this.member = member;

        if(ChronoUnit.DAYS.between(challengeStartDate, challengeEndDate) <= 7){
            this.tag = "1주";
        } else if(ChronoUnit.DAYS.between(challengeStartDate, challengeEndDate) <= 14){
            this.tag = "2주";
        } else if(ChronoUnit.DAYS.between(challengeStartDate, challengeEndDate) <= 21){
            this.tag = "3주";
        } else {
            this.tag = "4주";
        }
    }

    public static Challenge createChallenge(ChallengeRequestDto requestDto, Member member){
        return Challenge.builder()
                .challengeTitle(requestDto.getChallengeTitle())
                .challengeContent(requestDto.getChallengeContent())
                .categoryName(requestDto.getCategoryName())
                .build();
    }
}
