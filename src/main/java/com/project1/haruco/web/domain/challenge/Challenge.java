package com.project1.haruco.web.domain.challenge;

import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.request.challenge.ChallengeRequestDto;
import com.project1.haruco.web.dto.request.challenge.PutChallengeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Entity
@Getter
@Table(indexes = {@Index(name = "idx_status_progress", columnList = "challenge_status, challenge_progress"),
        @Index(name = "idx_status", columnList = "challenge_status")})
public class Challenge extends Timestamped implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(nullable = false)
    private String challengeTitle;

    @Column(columnDefinition="TEXT")
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

    @Column(name = "challenge_status", nullable = false)
    private boolean challengeStatus; // 삭제 여부

    @Column(name = "challenge_progress", nullable = false)
    private Long challengeProgress;

    @Column(length = 3000)
    private String challengeImgUrl;

    @Column
    private String challengeGood;

    @Column
    private String challengeBad;

    @Column
    private String challengeHoliday;

    @Column
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Challenge(String challengeTitle, String challengeContent,
                     CategoryName categoryName, String challengePassword,
                     LocalDateTime challengeStartDate, LocalDateTime challengeEndDate,
                     String challengeImgUrl, String challengeGood, String challengeBad,
                     String challengeHoliday, Member member) {
        this.challengeTitle = challengeTitle;
        this.challengeContent = challengeContent;
        this.categoryName = categoryName;
        this.challengePassword = challengePassword;
        this.challengeStartDate = challengeStartDate;
        this.challengeEndDate = challengeEndDate;
        this.challengeStatus = true;
        this.challengeProgress = 1L;
        this.challengeImgUrl = challengeImgUrl;
        this.challengeGood = challengeGood;
        this.challengeBad = challengeBad;
        this.challengeHoliday = challengeHoliday;
        this.member = member;

        if (ChronoUnit.DAYS.between(challengeStartDate, challengeEndDate) <= 7) {
            this.tag = "1주";
        } else if (ChronoUnit.DAYS.between(challengeStartDate, challengeEndDate) <= 14) {
            this.tag = "2주";
        } else if (ChronoUnit.DAYS.between(challengeStartDate, challengeEndDate) <= 21) {
            this.tag = "3주";
        } else {
            this.tag = "4주";
        }
    }

    public static Challenge createChallenge(ChallengeRequestDto requestDto,
                                            Member member) {
        return Challenge.builder()
                .challengeTitle(requestDto.getChallengeTitle())
                .challengeContent(requestDto.getChallengeContent())
                .categoryName(requestDto.getCategoryName())
                .challengePassword(requestDto.getChallengePassword())
                .challengeStartDate(requestDto.getChallengeStartDate())
                .challengeEndDate(requestDto.getChallengeEndDate())
                .challengeImgUrl(requestDto.getChallengeImgUrl())
                .challengeGood(requestDto.getChallengeGood())
                .challengeBad(requestDto.getChallengeBad())
                .challengeHoliday(requestDto.getChallengeHoliday())
                .member(member)
                .build();
    }

    public void setChallengeStatusFalse() {
        this.challengeStatus = false;
    }

    public void updateChallengeProgress(Long challengeProgress) {
        this.challengeProgress = challengeProgress;
    }

    public void putChallenge(PutChallengeRequestDto requestDto) {
        this.challengeTitle = requestDto.getChallengeTitle();
        this.challengeContent = requestDto.getChallengeContent();
        this.challengePassword = requestDto.getChallengePassword();
        this.challengeStartDate = requestDto.getChallengeStartDate();
        this.challengeEndDate = requestDto.getChallengeEndDate();
        this.challengeImgUrl = requestDto.getChallengeImgUrl();
        this.challengeGood = requestDto.getChallengeGood();
        this.challengeBad = requestDto.getChallengeBad();
        this.challengeHoliday = requestDto.getChallengeHoliday();
        if (ChronoUnit.DAYS.between(requestDto.getChallengeStartDate(), requestDto.getChallengeEndDate()) <= 7) {
            this.tag = "1주";
        } else if (ChronoUnit.DAYS.between(requestDto.getChallengeStartDate(), requestDto.getChallengeEndDate()) <= 14) {
            this.tag = "2주";
        } else if (ChronoUnit.DAYS.between(requestDto.getChallengeStartDate(), requestDto.getChallengeEndDate()) <= 21) {
            this.tag = "3주";
        } else {
            this.tag = "4주 이상";
        }
    }
}
