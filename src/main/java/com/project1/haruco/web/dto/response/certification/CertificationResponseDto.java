package com.project1.haruco.web.dto.response.certification;

import com.project1.haruco.web.domain.certification.Certification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
public class CertificationResponseDto {
    private Long memberId;
    private String nickName;
    private String profileImg;

    public CertificationResponseDto(Certification certification){
        this.memberId = certification.getMember().getMemberId();
        this.nickName = certification.getMember().getNickname();
        this.profileImg = certification.getMember().getProfileImg();
    }
}
