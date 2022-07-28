package com.project1.haruco.web.dto.response.mypage;

import com.project1.haruco.web.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MyPageResponseDto {
    private Long memberId;
    private String nickname;
    private String password;
    private String profileImg;
    private Long point;

    public MyPageResponseDto(Member member, Long totalPoint){
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.password = member.getPassword();
        this.profileImg = member.getProfileImg();
        this.point = totalPoint;
    }
}
