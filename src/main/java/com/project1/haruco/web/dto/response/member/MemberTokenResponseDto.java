package com.project1.haruco.web.dto.response.member;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.token.TokenDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberTokenResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private MemberResponseDto userInfo;

    @Builder
    public MemberTokenResponseDto(TokenDto tokenDto, Member member, int challengeCount, int completeChallengeCount){
        this.grantType = tokenDto.getGrantType();
        this.accessToken = tokenDto.getAccessToken();
        this.refreshToken = tokenDto.getRefreshToken();
        this.accessTokenExpiresIn = tokenDto.getAccessTokenExpiresIn();
        this.userInfo = new MemberResponseDto(member, challengeCount, completeChallengeCount);
    }

    public static MemberTokenResponseDto createMemberTokenResponseDto(TokenDto tokenDto,
                                                                      Member member,
                                                                      int challengeCount,
                                                                      int completeChallengeCount){

        return MemberTokenResponseDto.builder()
                .tokenDto(tokenDto)
                .member(member)
                .challengeCount(challengeCount)
                .completeChallengeCount(completeChallengeCount)
                .build();
    }
}
