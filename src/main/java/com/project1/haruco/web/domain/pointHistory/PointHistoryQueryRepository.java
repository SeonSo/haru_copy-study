package com.project1.haruco.web.domain.pointHistory;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.mypage.history.MemberHistoryDto;
import com.project1.haruco.web.dto.response.mypage.history.QMemberHistoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project1.haruco.web.domain.challenge.QChallenge.challenge;
import static com.project1.haruco.web.domain.challengeRecord.QChallengeRecord.challengeRecord;
import static com.project1.haruco.web.domain.member.QMember.member;
import static com.project1.haruco.web.domain.pointHistory.QPointHistory.pointHistory;
import static com.project1.haruco.web.domain.posting.QPosting.posting;

@RequiredArgsConstructor
@Repository
public class PointHistoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<MemberHistoryDto> findHistoryPosting(Member member2) {
        return queryFactory.select(new QMemberHistoryDto(
                        pointHistory.pointHistoryId,
                        pointHistory.createdAt,
                        challenge.challengeTitle,
                        pointHistory.getPoint,
                        member.memberId,
                        member.nickname,
                        member.profileImg
                ))
                .from(pointHistory)
                .innerJoin(pointHistory.posting, posting)
                .innerJoin(posting.challenge, challenge)
                .innerJoin(posting.member, member)
                .where(member.eq(member2),
                        pointHistory.status.isTrue())
                .fetch();
    }

    public List<MemberHistoryDto> findHistoryChallenge(Member member2){
        return queryFactory.select(new QMemberHistoryDto(
                        pointHistory.pointHistoryId,
                        pointHistory.createdAt,
                        challenge.challengeTitle,
                        pointHistory.getPoint,
                        member.memberId,
                        member.nickname,
                        member.profileImg
                ))
                .from(pointHistory)
                .join(pointHistory.challengeRecord, challengeRecord)
                .join(challengeRecord.member,member)
                .join(challengeRecord.challenge,challenge)
                .where(member.eq(member2),
                        pointHistory.status.isTrue())
                .fetch();
    }
}