package com.project1.haruco.web.dto.response.challenge;

import com.project1.haruco.web.domain.challenge.CategoryName;
import com.project1.haruco.web.domain.challenge.Challenge;
<<<<<<< HEAD
=======
import lombok.Builder;
>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ChallengeResponseDto {
<<<<<<< HEAD

    private Long challengeId;
    private String memberName; // 챌린지 만든 사람
    private String challengeTitle;
    private String challengeContent;
    private CategoryName categoryName; // enum
    private String challengePassword;
    private LocalDateTime challengeStartDate;
    private LocalDateTime challengeEndDate;
    private Long challengeProgress;
    private String challengeImgUrl;
    private String challengeGood;
    private String challengeBad;
    private String challengeHoliday;
    private List<Long> challengeMember;

    public ChallengeResponseDto(Challenge challenge,
                                List<Long> challengeMember) {
        this.challengeId = challenge.getChallengeId();
        this.memberName = challenge.getMember().getNickname();
        this.challengeTitle = challenge.getChallengeTitle();
        this.challengeContent = challenge.getChallengeContent();
        this.categoryName = challenge.getCategoryName();
        this.challengePassword = challenge.getChallengePassword();
        this.challengeStartDate = challenge.getChallengeStartDate();
        this.challengeEndDate = challenge.getChallengeEndDate();
        this.challengeProgress = challenge.getChallengeProgress();
        this.challengeImgUrl = challenge.getChallengeImgUrl();
        this.challengeGood = challenge.getChallengeGood();
        this.challengeBad = challenge.getChallengeBad();
        this.challengeHoliday = challenge.getChallengeHoliday();
=======
    private Long challengeId;
    private Long memberId;
    private String memberName;
    private String challengeTitle;
    private String challengeContent;
    private CategoryName categoryName;
    private String challengePassword;
    private LocalDateTime challengeStartDate;
    private LocalDateTime challengeEndDate;
    private Long challengeProgress;
    private String challengeImgUrl;
    private String challengeGood;
    private String challengeBad;
    private String challengeHoliday;
    private String tag;
    private Set<Long> challengeMember = new HashSet<>();

    @Builder
    public ChallengeResponseDto(Long challengeId,
                                Long memberId,
                                String memberName,
                                String challengeTitle,
                                String challengeContent,
                                CategoryName categoryName,
                                String challengePassword,
                                LocalDateTime challengeStartDate,
                                LocalDateTime challengeEndDate,
                                Long challengeProgress,
                                String challengeImgUrl,
                                String challengeGood,
                                String challengeBad,
                                String challengeHoliday,
                                String tag,
                                Set<Long> challengeMember) {
        this.challengeId = challengeId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.challengeTitle = challengeTitle;
        this.challengeContent = challengeContent;
        this.categoryName = categoryName;
        this.challengePassword = challengePassword;
        this.challengeStartDate = challengeStartDate;
        this.challengeEndDate = challengeEndDate;
        this.challengeProgress = challengeProgress;
        this.challengeImgUrl = challengeImgUrl;
        this.challengeGood = challengeGood;
        this.challengeBad = challengeBad;
        this.challengeHoliday = challengeHoliday;
        this.tag = tag;
>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
        this.challengeMember = challengeMember;
    }

    public static ChallengeResponseDto createChallengeResponseDto(Challenge challenge, Set<Long> challengeMember) {
        return ChallengeResponseDto.builder()
                .challengeId(challenge.getChallengeId())
                .memberId(challenge.getMember().getMemberId())
                .memberName(challenge.getMember().getNickname())
                .challengeTitle(challenge.getChallengeTitle())
                .challengeContent(challenge.getChallengeContent())
                .categoryName(challenge.getCategoryName())
                .challengePassword(challenge.getChallengePassword())
                .challengeStartDate(challenge.getChallengeStartDate())
                .challengeEndDate(challenge.getChallengeEndDate())
                .challengeProgress(challenge.getChallengeProgress())
                .challengeImgUrl(challenge.getChallengeImgUrl())
                .challengeGood(challenge.getChallengeGood())
                .challengeBad(challenge.getChallengeBad())
                .challengeHoliday(challenge.getChallengeHoliday())
                .tag(challenge.getTag())
                .challengeMember(challengeMember)
                .build();
    }
}
