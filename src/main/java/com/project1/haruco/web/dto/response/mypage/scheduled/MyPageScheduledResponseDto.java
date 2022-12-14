package com.project1.haruco.web.dto.response.mypage.scheduled;

import com.project1.haruco.util.Scheduler;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.mypage.CalculateLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class MyPageScheduledResponseDto {
    private Long memberId;
    private String nickname;
    private String profileImage;
    private Long point;
    private Long level;
    private List<ScheduledResponseDto> challengeList;

    @Builder
    public MyPageScheduledResponseDto(Member member, List<ScheduledResponseDto> challengeList){
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImg();
        this.point = member.getPoint().getAcquiredPoint();
        this.challengeList = challengeList;
        this.level = CalculateLevel.calculateLevel(member.getPoint().getAcquiredPoint());
    }

    public static MyPageScheduledResponseDto createMyPageScheduledResponseDto(Member member, List<ScheduledResponseDto> challengeList){

        return MyPageScheduledResponseDto.builder()
                .member(member)
                .challengeList(challengeList)
                .build();
    }
}
