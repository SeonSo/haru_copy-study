package com.project1.haruco.util;

import com.project1.haruco.web.domain.posting.Posting;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class RemainingMember {

    private Posting posting;
    private Long challengeRecordId;
    private boolean challengeRecordStatus;

    @QueryProjection
    public RemainingMember(Posting posting, Long challengeRecordId, boolean challengeRecordStatus) {
        this.posting = posting;
        this.challengeRecordId = challengeRecordId;
        this.challengeRecordStatus = challengeRecordStatus;
    }
}
