package com.project1.haruco.web.dto.request.posting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@ToString
@Getter

public class PostingRequestDto {
    private String postingImg;
    private String postingContent;
    private Long memberId;
    private Long challengeId;
}
