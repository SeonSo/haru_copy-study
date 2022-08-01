package com.project1.haruco.web.dto.response.mypage.proceed;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.mypage.CalculateLevel;
import com.project1.haruco.web.dto.response.mypage.scheduled.MyPageScheduledResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class MyPageProceedResponseDto {
    private Long memberId;
    private String nickname;
    private String profileImage;
    private Long point;
    private Long level;
    private List<ProceedResponseDto> challengeList;

    @Builder
    public MyPageProceedResponseDto(Member member, Long totalPoint, List<ProceedResponseDto> challengeList){
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImg();
        this.point = totalPoint;
        this.challengeList = challengeList;
        this.level = CalculateLevel.calculateLevel(member.getPoint().getAcquiredPoint());
    }

    public static MyPageScheduledResponseDto createMyPageProceedResponseDto(Member member,
                                                                            Long totalPoint,
                                                                            List<ProceedResponseDto> challengeList){

        return MyPageScheduledResponseDto.builder()
                .member(member)
                .totalPoint(totalPoint)
                .challengeList(challengeList)
                .build();
    }
}
