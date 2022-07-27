package com.project1.haruco.web.dto.response.posting;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.member.Member;
import lombok.Getter;

@Getter
public class PostingRequestDto {

    private String postingImg;
    private String postingContent;
    private boolean postingStatus;
    private boolean postingApproval;
    private boolean postingModifyOk;
    private boolean postingPoint;
    private Long postingPoint;
    private Member member;
    private Challenge challenge;
}
