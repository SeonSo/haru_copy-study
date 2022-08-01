package com.project1.haruco.web.dto.response.mypage.proceed;

import com.project1.haruco.web.domain.challenge.CategoryName;
import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ProceedResponseDto {
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
    private int participateSize;

    public ProceedResponseDto(Challenge challenge, List<ChallengeRecord> participate) {
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
        this.successPercent = percentProcess(challenge.getChallengeStartDate(), challenge.getChallengeEndDate());
        this.participateSize = participateImg.size();
    }

    public List<String> getImg(List<ChallengeRecord> recordList, Challenge challenge) {
        List<String> imageList = recordList.stream()
                .filter(challengeRecord -> challengeRecord.getChallenge().equals(challenge))
                .map(challengeRecord -> challengeRecord.getMember().getProfileImg())
                .collect(Collectors.toList());
        return imageList;
    }

    public int percentProcess(LocalDateTime startDate, LocalDateTime endDate){
        LocalDate startDate2 = startDate.toLocalDate();
        LocalDate endDate2 = endDate.toLocalDate();

        LocalDate nowTime = LocalDateTime.now().toLocalDate();
        Long period1 = ChronoUnit.DAYS.between(startDate2, endDate2);
        Long period2 = ChronoUnit.DAYS.between(startDate2, nowTime);

        double mom = (double) period1;
        double son = (double) period2;

        double result = (son / mom) * 100;

        int processTime = (int) result;
        return processTime;
    }

}
