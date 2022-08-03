package com.project1.haruco.util;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challenge.ChallengeRepository;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecordRepository;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.member.MemberRepository;
import com.project1.haruco.web.domain.point.Point;
import com.project1.haruco.web.domain.point.PointRepository;
import com.project1.haruco.web.domain.pointHistory.PointHistory;
import com.project1.haruco.web.domain.pointHistory.PointHistoryRepository;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.domain.posting.PostingRepository;
import com.project1.haruco.web.dto.query.posting.SchedulerIdListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.project1.haruco.web.domain.challenge.CategoryName.OFFICIAL;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final PostingRepository postingRepository;
    private final ChallengeRecordRepository challengeRecordRepository;
    private final ChallengeRepository challengeRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final MemberRepository memberRepository;
    private final SchedulerQueryRepository schedulerQueryRepository;
    private final PointRepository pointRepository;

    private static final String SCHEDULE_MODE = System.getProperty("schedule.mode");

    LocalDateTime today;

    @Scheduled(cron = "01 0 0 * * *") // 초, 분, 시, 일, 월, 주 순서
    @Transactional
    public void notWrittenMemberKick() {

        // nginx 사용시에 여러 인스턴스 모두에서 update 되는 것을 방지.
        if (isNotScheduleMode()) {
            return;
        }
        // today 호출, 스케줄러 실행시의 시간으로 변경.
        initializeToday();

        // 주말 여부 체크를 위한 week 변수 생성
        int week = today.getDayOfWeek().getValue();

        // 주말 여부를 체크해서 챌린지 레코드를 가져옴.
        List<Long> challengeId = schedulerQueryRepository.findAllByChallenge(week);

        // 작성하지 않은 인원 가져옴
        List<SchedulerIdListDto> notWrittenList = schedulerQueryRepository.findNotWrittenList(challengeId);

        List<Long> notWrittenMember = getKickMember(notWrittenList);
        List<Long> notWrittenChallenge = getKickChallenge(notWrittenList);

        // 벌크쿼리로 challengeRecordStatus false로 변경.
        int notWrittenChallengeRecordKick = challengeRecordRepository.kickMemberOnChallenge(notWrittenMember, notWrittenChallenge);
        log.info("포스팅 작성하지 않은 인원 update : {} ", notWrittenChallengeRecordKick);
    }

    @Scheduled(cron = "04 0 0 * * *") // 초, 분, 시, 일, 월, 주 순서
    @Transactional
    public void changePostingApproval() {
        if (isNotScheduleMode()) {
            return;
        }

        // 진행중인 챌린지에서 포스팅 작성을 한 사람을 찾아온 후에 서브쿼리로 챌린지 참여 인원을 체크,
        // 포스팅의 인증카운트가 전체 멤버의 50%가 넘는 인원을 가져온다.
        List<Posting> approvalPostingList = schedulerQueryRepository.findChallengeMember();

        // 포스팅의 인증여부 업데이트.
        int postingApprovalUpdate = postingRepository.updatePostingApproval(approvalPostingList);
        log.info("postingApprovalUpdate 벌크 연산 result: {} ", postingApprovalUpdate);

        // 포인트가 인증되었으니 히스토리 추가.
        List<PointHistory> pointHistoryList = approvalPostingList.stream()
                .map(posting -> PointHistory.createPostingPointHistory(1L, posting))
                .collect(Collectors.toList());

        pointHistoryRepository.saveAll(pointHistoryList);

        //포인트 업데이트해줄 멤버 id 가져옴.
        List<Long> memberList = approvalPostingList.stream()
                .map(posting -> posting.getMember().getMemberId())
                .collect(Collectors.toList());

        // 포인트 업데이트.
        int updatePoint = pointRepository.updatePoint(memberList);
        log.info("updatePoint 벌크 연산 result: {} ", updatePoint);
    }

    @Scheduled(cron = "05 0 0 * * *") // 초, 분, 시, 일, 월, 주 순서
    @Transactional
    public void updatePostingModifyOk() {

        if (isNotScheduleMode()) {
            return;
        }
        // 작성 당일이 지나면 수정 불가능하게 만드는 쿼리
        List<Long> postingIdList = schedulerQueryRepository.findSchedulerUpdatePostingModifyOk(today);
        // 벌크성 쿼리 업데이트
        long updateResult = postingRepository.updatePostingModifyOk(postingIdList);
        log.info("updateResult 벌크 연산 result: {} ", updateResult);
    }

    @Scheduled(cron = "07 0 0 * * *") // 초, 분, 시, 일, 월, 주 순서
    @Transactional
    public void challengeStatusUpdate() {

        if (isNotScheduleMode()) {
            return;
        }
        List<ChallengeRecord> recordList = schedulerQueryRepository.findAllByChallengeProgressLessThan(3L);

        List<Challenge> challengeList = recordList
                .stream()
                .map(ChallengeRecord::getChallenge)
                .distinct()
                .collect(Collectors.toList());

        List<Challenge> startList = challengeList
                .stream()
                .filter(this::isChallengeTimeToStart)
                .collect(Collectors.toList());

        List<Challenge> endList = challengeList
                .stream()
                .filter(this::isChallengeTimeToEnd)
                .collect(Collectors.toList());

        // 챌린지 시작
        challengeStart(startList);

        // 챌린지 종료
        challengeEnd(endList);

        // 챌린지 완주 포인트 지급
        challengeEndPoint(endList);
    }

    private void challengeStart(List<Challenge> startList) {
        Long result1 = schedulerQueryRepository.updateChallengeProgress(2L, startList);
        log.info(today + " / " + result1 + " Challenge Start");
    }

    private void challengeEnd(List<Challenge> endList) {
        Long result2 = schedulerQueryRepository.updateChallengeProgress(3L, endList);
        Long result3 = schedulerQueryRepository.updateChallengePoint(endList);
        log.info(today + " / " + result2 + " Challenge End, " + result3 + "challengePoint update");
    }

    private void challengeEndPoint(List<Challenge> endList) {
        long result3 = endList
                .stream()
                .peek(this::getPointWhenChallengeEnd)
                .count();
        log.info(today + " / " + result3 + " members get points");
    }

    private void getPointWhenChallengeEnd(Challenge challenge) {
        List<ChallengeRecord> recordList = schedulerQueryRepository.findAllByChallengeOnScheduler(challenge);

        List<Member> memberList = recordList
                .stream()
                .map(ChallengeRecord::getMember)
                .collect(Collectors.toList());

        if (memberList.size() > 0) {
            Long postingCount = schedulerQueryRepository.findAllByChallengeAndFirstMember(challenge, memberList.get(0));
            Long resultPoint = postingCount * 50L * (challenge.getCategoryName().equals(OFFICIAL) ? 2L : 1L);

            if (resultPoint != 0L) {
                List<PointHistory> pointHistoryList = recordList
                        .stream()
                        .map(r -> PointHistory.createChallengePointHistory(resultPoint, r))
                        .collect(Collectors.toList());
                pointHistoryRepository.saveAll(pointHistoryList);

                List<Point> pointList = memberList
                        .stream()
                        .map(Member::getPoint)
                        .collect(Collectors.toList());
                schedulerQueryRepository.updatePointAll(pointList, resultPoint);
            }
        }
    }

    private boolean isChallengeTimeToStart(Challenge c) {
        return c.getChallengeProgress() == 1L && c.getChallengeStartDate().isEqual(today);
    }

    private boolean isChallengeTimeToEnd(Challenge c) {
        return c.getChallengeProgress() == 2L && c.getChallengeEndDate().isBefore(today);
    }

    private List<Long> getKickChallenge(List<SchedulerIdListDto> postingList) {
        return postingList.stream()
                .map(SchedulerIdListDto::getChallengeId).distinct()
                .collect(Collectors.toList());
    }

    private List<Long> getKickMember(List<SchedulerIdListDto> postingList) {
        return postingList.stream()
                .map(SchedulerIdListDto::getMemberId).distinct()
                .collect(Collectors.toList());
    }

    public void initializeToday() {

        today = LocalDate.now().atStartOfDay();
    }

    private boolean isNotScheduleMode() {

        return null == SCHEDULE_MODE || !SCHEDULE_MODE.equals("on");

    }
}