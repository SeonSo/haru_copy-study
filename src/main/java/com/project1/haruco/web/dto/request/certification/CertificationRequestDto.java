package com.project1.haruco.web.dto.request.certification;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CertificationRequestDto {
    private Long postingId;
    private Long totalNumber;

    @Builder
    public CertificationRequestDto(Long postingId, Long totalNumber){
        this.postingId = postingId;
        this.totalNumber = totalNumber;
    }
}
