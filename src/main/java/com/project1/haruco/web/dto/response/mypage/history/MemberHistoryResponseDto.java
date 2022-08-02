package com.project1.haruco.web.dto.response.mypage.history;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.mypage.CalculateLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class MemberHistoryResponseDto {
    private Long memberId;
    private String nickname;
    private String profileImage;
    private Long point;
    private Long level;
    private int rank;

    private List<PointHistoryDto> postingGetPoint;
    private List<PointHistoryDto> challengeGetPoint;

    @Builder
    public MemberHistoryResponseDto(Member member,
                                    List<PointHistoryDto> postingGetPoint,
                                    List<PointHistoryDto> challengeGetPoint,
                                    int rank) {
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImg();
        this.point = member.getPoint().getAcquiredPoint();
        this.level = CalculateLevel.calculateLevel(member.getPoint().getAcquiredPoint());
        this.postingGetPoint = postingGetPoint;
        this.challengeGetPoint = challengeGetPoint;
        this.rank = rank;
    }

    public static MemberHistoryResponseDto createMemberHistoryResponseDto(Member member,
                                                                          List<PointHistoryDto> postingGetPoint,
                                                                          List<PointHistoryDto> challengeGetPoint,
                                                                          int rank){

        return MemberHistoryResponseDto.builder()
                .member(member)
                .postingGetPoint(postingGetPoint)
                .challengeGetPoint(challengeGetPoint)
                .rank(rank)
                .build();
    }
}
