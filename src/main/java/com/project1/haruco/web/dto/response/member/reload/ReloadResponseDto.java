package com.project1.haruco.web.dto.response.member.reload;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.member.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReloadResponseDto {
    private MemberResponseDto userInfo;

    @Builder
    public ReloadResponseDto(Member member, int challengeCount, int completeChallengeCount){
        this.userInfo = new MemberResponseDto(Member member, int challengeCount, int completeChallengeCount);
    }

    public static ReloadResponseDto createReloadResponseDto(Member member, int challengeCount, int completeChallengeCount){
        return ReloadResponseDto.builder()
                .member(member)
                .challengeCount(challengeCount)
                .completeChallengeCount(completeChallengeCount)
                .build();
    }
}
