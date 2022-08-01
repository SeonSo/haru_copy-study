package com.project1.haruco.web.domain.pointHistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.posting.Posting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@NoArgsConstructor
@Entity
@Getter
public class PointHistory extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "point_history_id")
    private Long pointHistoryId;

    @Column
    private Long getPoint;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_record_id")
    private ChallengeRecord challengeRecord;

    @Column
    private boolean status;

    @Builder
    public PointHistory(Long pointHistoryId, Long getPoint,
                        Posting posting, ChallengeRecord challengeRecord,
                        boolean status){
        this.pointHistoryId = pointHistoryId;
        this.getPoint = getPoint;
        this.posting = posting;
        this.challengeRecord = challengeRecord;
        this.status = status;
    }

    public static PointHistory createPostingPointHistory(Long getPoint, Posting posting) {
        return PointHistory.builder()
                .getPoint(getPoint)
                .posting(posting)
                .status(true)
                .build();
    }

    public static PointHistory createChallengePointHistory(Long resultPoint, ChallengeRecord challengeRecord) {
        return PointHistory.builder()
                .getPoint(resultPoint)
                .challengeRecord(challengeRecord)
                .build();
    }

    public void updateStatus() {
        this.status = false;
    }
}
