package com.project1.haruco.web.dto.request.challenge;

import com.project1.haruco.web.domain.challenge.CategoryName;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChallengeRequestDto {
<<<<<<< HEAD
    private String challengeTitle;
    private String challengeContent;
    private String challengePassword;
    private CategoryName categoryName;
    private LocalDateTime challengeStartDate;
    private LocalDateTime challengeEndDate;
    private String challengeImgUrl;
    private String challengeGood;
    private String challengeBad;
    private String challengeHoliday;


    public ChallengeRequestDto(String challengeTitle, String challengeContent, String challengePassword, CategoryName categoryName,
                               LocalDateTime challengeStartDate, LocalDateTime challengeEndDate, String challengeImgUrl,
                               String challengeGood, String challengeBad, String challengeHoliday){
=======
    private final String challengeTitle;
    private final String challengeContent;
    private final String challengePassword;
    private final CategoryName categoryName;
    private final LocalDateTime challengeStartDate;
    private final LocalDateTime challengeEndDate;
    private final String challengeImgUrl;
    private final String challengeGood;
    private final String challengeBad;
    private final String challengeHoliday;

    @Builder
    public ChallengeRequestDto(String challengeTitle,
                               String challengeContent,
                               String challengePassword,
                               CategoryName categoryName,
                               LocalDateTime challengeStartDate,
                               LocalDateTime challengeEndDate,
                               String challengeImgUrl,
                               String challengeGood,
                               String challengeBad,
                               String challengeHoliday){

>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
        this.challengeTitle = challengeTitle;
        this.challengeContent = challengeContent;
        this.challengePassword = challengePassword;
        this.categoryName = categoryName;
<<<<<<< HEAD
        this.challengeStartDate = challengeStartDate;
        this.challengeEndDate = challengeEndDate;
=======
        this.challengeStartDate = challengeStartDate.withHour(0).withMinute(0).withSecond(0);
        this.challengeEndDate = challengeEndDate.withHour(23).withMinute(59).withSecond(57);
>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
        this.challengeImgUrl = challengeImgUrl;
        this.challengeGood = challengeGood;
        this.challengeBad = challengeBad;
        this.challengeHoliday = challengeHoliday;
    }
<<<<<<< HEAD
=======

    public static ChallengeRequestDto createChallengeRequestDto(String challengeTitle,
                                                                String challengeContent,
                                                                String challengePassword,
                                                                CategoryName categoryName,
                                                                LocalDateTime challengeStartDate,
                                                                LocalDateTime challengeEndDate,
                                                                String challengeImgUrl,
                                                                String challengeGood,
                                                                String challengeBad,
                                                                String challengeHoliday) {
        return ChallengeRequestDto.builder()
                .challengeTitle(challengeTitle)
                .challengeContent(challengeContent)
                .challengePassword(challengePassword)
                .categoryName(categoryName)
                .challengeStartDate(challengeStartDate)
                .challengeEndDate(challengeEndDate)
                .challengeImgUrl(challengeImgUrl)
                .challengeGood(challengeGood)
                .challengeBad(challengeBad)
                .challengeHoliday(challengeHoliday)
                .build();
    }
>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
}
