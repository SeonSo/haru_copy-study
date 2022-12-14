package com.project1.haruco.web.dto.request.posting;

import lombok.*;


import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter

public class PostingCreateRequestDto {
    private String postingImg;

    @NotBlank(message = "빈 내용")
    private String postingContent;
    private Long memberId;
    private Long challengeId;
    private Long totalNumber;

    @Builder
    public PostingCreateRequestDto(String postingImg, String postingContent,
                                   Long memberId, Long challengeId, Long totalNumber){
        this.postingImg = postingImg;
        this.postingContent = postingContent;
        this.memberId = memberId;
        this.challengeId = challengeId;
        this.totalNumber = totalNumber;
    }
}
