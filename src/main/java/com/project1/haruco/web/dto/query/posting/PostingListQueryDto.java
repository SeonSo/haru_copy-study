package com.project1.haruco.web.dto.query.posting;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
public class PostingListQueryDto {
    private Long postingId;
    private Long memberId;
    private String nickname;
    private String profileImg;
    private String postingImg;
    private String postingContent;
    private boolean postingApproval;
    private boolean postingModifyOk;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long postingCount;

    @QueryProjection
    public PostingListQueryDto(Long postingId, Long memberId, String nickname, String profileImg,
                               String postingImg, String postingContent,
                               boolean postingApproval, boolean postingModifyOk,
                               LocalDateTime createdAt, LocalDateTime modifiedAt, Long postingCount){
        this.postingId = postingId;
        this.memberId =memberId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.postingImg = postingImg;
        this.postingContent = postingContent;
        this.postingApproval = postingApproval;
        this.postingModifyOk = postingModifyOk;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.postingCount = postingCount;
    }
}
