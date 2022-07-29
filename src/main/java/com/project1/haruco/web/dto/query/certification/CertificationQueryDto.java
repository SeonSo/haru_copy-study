package com.project1.haruco.web.dto.query.certification;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CertificationQueryDto {
    private Long postingId;
    private Long memberId;

    @QueryProjection
    public CertificationQueryDto(Long postingId, Long memberId){
        this.postingId = postingId;
        this.memberId = memberId;
    }
}
