package com.project1.haruco.util;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import com.project1.haruco.web.domain.challengeRecord.QChallengeRecord;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.point.Point;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.dto.query.posting.QSchedulerIdListDto;
import com.project1.haruco.web.dto.query.posting.SchedulerIdListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.project1.haruco.web.domain.challenge.QChallenge.challenge;
import static com.project1.haruco.web.domain.challengeRecord.QChallengeRecord.challengeRecord;
import static com.project1.haruco.web.domain.point.QPoint.point;
import static com.project1.haruco.web.domain.posting.QPosting.posting;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class SchedulerQueryRepository {

    private final JPAQueryFactory queryFactory;

    //진행중인 챌린지
    public List<Long> findAllByChallenge(int week){

        return queryFactory.select(challengeRecord.challenge.challengeId)
                .distinct()
                .from(challengeRecord)
                .join(challengeRecord.challenge,challenge)
                .where(isHoliday(week),
                        challengeRecord.challengeRecordStatus.isTrue(),
                        challengeRecord.challenge.challengeStatus.isTrue(),
                        challengeRecord.challenge.challengeProgress.eq(2L))
                .fetch();
    }

    //주말 여부
    private BooleanExpression isHoliday(int week){
        BooleanExpression notEmpty = challengeRecord.challenge.challengeHoliday.isNotEmpty();
        return week == 6 || week == 7 ? notEmpty : null;
    }

    //포스팅 없는 인원
    public List<SchedulerIdListDto> findNotWrittenList(List<Long> challengeId){

        return queryFactory.select(new QSchedulerIdListDto(
                        challengeRecord.challenge.challengeId,
                        challengeRecord.member.memberId)).distinct()
                .from(challengeRecord)
                .leftJoin(posting)
                .on(challengeRecord.challenge.challengeId.eq(posting.challenge.challengeId),
                        (challengeRecord.member.memberId.eq(posting.member.memberId)))
                .where(challengeRecord.challengeRecordStatus.isTrue(),
                        challengeRecord.challenge.challengeId.in(challengeId),
                        posting.isNull())
                .fetch();
    }

    //포스팅 등록 인원 확인용 포스팅
    public List<Posting> findChallengeMember() {
        QChallengeRecord challengeRecordSub = new QChallengeRecord("challengeRecordSub");

        return queryFactory.select(posting)
                .from(posting)
                .join(challengeRecord).on(challengeRecord.challenge.eq(posting.challenge),
                        posting.member.eq(challengeRecord.member))
                .join(posting.challenge,challenge)
                .where(challengeRecord.challengeRecordStatus.isTrue(),
                        challenge.challengeStatus.isTrue(),
                        challenge.challengeProgress.eq(2L),
                        posting.postingApproval.isFalse(),
                        posting.postingCount.goe(
                                JPAExpressions.select(challengeRecordSub.challengeRecordId.count().divide(2))
                                        .from(challengeRecordSub)
                                        .where(challengeRecordSub.challenge.challengeId.eq(challengeRecord.challenge.challengeId),
                                                challengeRecordSub.challengeRecordStatus.isTrue())
                        ))
                .fetch();
    }

    //수정 가능 여부(당일 한정)
    public List<Long> findSchedulerUpdatePostingModifyOk(LocalDateTime today) {
        return queryFactory.select(posting.postingId)
                .from(posting)
                .where(posting.postingStatus.isTrue(),
                        posting.postingModifyOk.isTrue(),
                        posting.createdAt.lt(today))
                .fetch();
    }

    //공식 챌린지
    public Boolean findAllByOfficialAndChallengeTitle(String title) {
        return queryFactory.select(challenge.challengeId)
                .from(challenge)
                .where(challenge.challengeStatus.isTrue(),
                        challenge.challengeProgress.eq(1L),
                        challenge.challengeTitle.eq(title))
                .fetchFirst() == null;
    }

    //챌린지 진행 상태
    @Modifying
    public Long updateChallengeProgress(Long progress, List<Challenge> challengeList) {
        return queryFactory.update(challenge)
                .set(challenge.challengeProgress, progress)
                .where(challenge.in(challengeList))
                .execute();
    }

    //포인트 상태
    @Modifying
    public Long updateChallengePoint(List<Challenge> challengeList) {
        return queryFactory.update(challengeRecord)
                .set(challengeRecord.challengePoint, true)
                .where(challengeRecord.challenge.in(challengeList))
                .execute();
    }

    //챌린지 완료시 포인트 지급
    public List<ChallengeRecord> findAllByChallengeOnScheduler(Challenge challenge) {
        return queryFactory.select(challengeRecord)
                .from(challengeRecord)
                .join(challengeRecord.member).fetchJoin()
                .where(challengeRecord.challengeRecordStatus.isTrue(),
                        challengeRecord.challenge.eq(challenge))
                .fetch();
    }

    //포스팅 개수
    public Long findAllByChallengeAndFirstMember(Challenge challenge, Member member) {
        return queryFactory.select(posting.challenge.challengeId)
                .from(posting)
                .where(posting.challenge.challengeStatus.isTrue(),
                        posting.challenge.eq(challenge),
                        posting.member.eq(member))
                .fetchCount();
    }

    //챌린지 완료 포인트 벌크 업데이트
    @Modifying
    public void updatePointAll(List<Point> pointList, Long getPoint) {
        queryFactory.update(point)
                .set(point.acquiredPoint, point.acquiredPoint.add(getPoint))
                .where(point.in(pointList))
                .execute();
    }

    //진행 상태 업데이트 목록
    public List<ChallengeRecord> findAllByChallengeProgressLessThan(Long progress) {
        return queryFactory
                .selectFrom(challengeRecord)
                .innerJoin(challengeRecord.challenge).fetchJoin()
                .where(challengeRecord.challengeRecordStatus.isTrue(),
                        challengeRecord.challenge.challengeStatus.isTrue(),
                        challengeRecord.challenge.challengeProgress.lt(progress))
                .fetch();
    }
}
