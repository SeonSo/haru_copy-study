package com.project1.haruco.web.dto.response.mypage.end;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.mypage.CalculateLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class MyPageEndResponseDto {

    private Long memberId;
    private String nickname;
    private String profileImage;
    private Long point;
    private Long level;
    private List<EndResponseDto> challengeList;

    @Builder
    public MyPageEndResponseDto(Member member, List<EndResponseDto> challengeList){
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImg();
        this.point = member.getPoint().getAcquiredPoint();
        this.challengeList = challengeList;
        this.level = CalculateLevel.calculateLevel(member.getPoint().getAcquiredPoint());
    }

    public static MyPageEndResponseDto createMyPageEndResponseDto(Member member, List<EndResponseDto> challengeList){

        return MyPageEndResponseDto.builder()
                .member(member)
                .challengeList(challengeList)
                .build();
    }
}
