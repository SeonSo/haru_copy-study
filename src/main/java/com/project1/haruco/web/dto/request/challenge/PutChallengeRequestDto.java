package com.project1.haruco.web.dto.request.challenge;

<<<<<<< HEAD
import com.project1.haruco.web.domain.challenge.CategoryName;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PutChallengeRequestDto {
    private Long challengeId;
    private CategoryName categoryName;
=======
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PutChallengeRequestDto {
    private Long challengeId;
>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
    private String challengeContent;
    private String challengeTitle;
    private LocalDateTime challengeStartDate;
    private LocalDateTime challengeEndDate;
    private String challengeImgUrl;
    private String challengePassword;
    private String challengeGood;
    private String challengeBad;
    private String challengeHoliday;
<<<<<<< HEAD
}
=======
}
>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
