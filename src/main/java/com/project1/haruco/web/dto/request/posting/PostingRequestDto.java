package com.project1.haruco.web.dto.request.posting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class PostingRequestDto {
    private String postingImg;
    private String postingContent;
    private Long memberId;
    private Long challengeId;

}
