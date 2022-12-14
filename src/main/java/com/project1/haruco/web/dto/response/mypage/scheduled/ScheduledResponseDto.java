package com.project1.haruco.web.dto.response.mypage.scheduled;

import com.project1.haruco.web.domain.challenge.CategoryName;
import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ScheduledResponseDto {
    private Long challengeId;
    private String challengeTitle;
    private String challengeContent;
    private CategoryName categoryName;
    private Long challengeProgress;
    private String challengeImgUrl;
    private Long challengeMember;
    private LocalDateTime challengeStartDate;
    private LocalDateTime challengeEndDate;
    private int successPercent;
    private List<String> participateImg;
    private long participateSize;

    public ScheduledResponseDto(Challenge challenge, List<ChallengeRecord> participate) {
        this.challengeId = challenge.getChallengeId();
        this.challengeTitle = challenge.getChallengeTitle();
        this.challengeContent = challenge.getChallengeContent();
        this.categoryName = challenge.getCategoryName();
        this.challengeProgress = challenge.getChallengeProgress();
        this.challengeImgUrl = challenge.getChallengeImgUrl();
        this.challengeMember = challenge.getMember().getMemberId();
        this.challengeStartDate = challenge.getChallengeStartDate();
        this.challengeEndDate = challenge.getChallengeEndDate();
        this.participateImg = getImg(participate,challenge);
        this.successPercent = 0;
        this.participateSize = participateImg.size();
}

    private List<String> getImg(List<ChallengeRecord> recordList, Challenge challenge) {
        List<String> imageList = recordList.stream()
                .filter(challengeRecord -> challengeRecord.getChallenge().equals(challenge))
                .map(challengeRecord -> challengeRecord.getMember().getProfileImg())
                .collect(Collectors.toList());
        return imageList;
    }
}
