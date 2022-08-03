package com.project1.haruco.web.dto.response.member;

import com.project1.haruco.web.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberTokenResponseDto {
    private MemberResponseDto userInfo;

    @Builder
    public MemberTokenResponseDto(Member member, int challengeCount, int completeChallengeCount){
        this.userInfo = new MemberResponseDto(member, challengeCount, completeChallengeCount);
    }

    public static MemberTokenResponseDto createMemberTokenResponseDto(Member member,
                                                                      int challengeCount,
                                                                      int completeChallengeCount){

        return MemberTokenResponseDto.builder()
                .member(member)
                .challengeCount(challengeCount)
                .completeChallengeCount(completeChallengeCount)
                .build();
    }
}
