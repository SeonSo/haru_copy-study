package com.project1.haruco.service;

import com.project1.haruco.exception.ApiRequestException;
import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecordQueryRepository;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.member.MemberRepository;
import com.project1.haruco.web.domain.point.Point;
import com.project1.haruco.web.domain.point.PointRepository;
import com.project1.haruco.web.domain.pointHistory.PointHistoryQueryRepository;
import com.project1.haruco.web.dto.request.login.LoginRequestDto;
import com.project1.haruco.web.dto.request.mypage.ProfileUpdateRequestDto;
import com.project1.haruco.web.dto.request.mypage.PwUpdateRequestDto;
import com.project1.haruco.web.dto.request.signup.SignupRequestDto;
import com.project1.haruco.web.dto.response.member.MemberTokenResponseDto;
import com.project1.haruco.web.dto.response.member.reload.ReloadResponseDto;
import com.project1.haruco.web.dto.response.mypage.MyPageResponseDto;
import com.project1.haruco.web.dto.response.mypage.end.EndResponseDto;
import com.project1.haruco.web.dto.response.mypage.end.MyPageEndResponseDto;
import com.project1.haruco.web.dto.response.mypage.history.MemberHistoryDto;
import com.project1.haruco.web.dto.response.mypage.history.MemberHistoryResponseDto;
import com.project1.haruco.web.dto.response.mypage.history.PointHistoryDto;
import com.project1.haruco.web.dto.response.mypage.proceed.MyPageProceedResponseDto;
import com.project1.haruco.web.dto.response.mypage.proceed.ProceedResponseDto;
import com.project1.haruco.web.dto.response.mypage.scheduled.MyPageScheduledResponseDto;
import com.project1.haruco.web.dto.response.mypage.scheduled.ScheduledResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.project1.haruco.web.dto.response.member.MemberTokenResponseDto.createMemberTokenResponseDto;
import static com.project1.haruco.web.dto.response.member.reload.ReloadResponseDto.createReloadResponseDto;
import static com.project1.haruco.web.dto.response.mypage.MyPageResponseDto.createMyPageResponseDto;
import static com.project1.haruco.web.dto.response.mypage.end.MyPageEndResponseDto.createMyPageEndResponseDto;
import static com.project1.haruco.web.dto.response.mypage.history.MemberHistoryResponseDto.createMemberHistoryResponseDto;
import static com.project1.haruco.web.dto.response.mypage.proceed.MyPageProceedResponseDto.createMyPageProceedResponseDto;
import static com.project1.haruco.web.dto.response.mypage.scheduled.MyPageScheduledResponseDto.createMyPageScheduledResponseDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PointRepository pointRepository;
    private final ChallengeRecordQueryRepository challengeRecordQueryRepository;
    private final PointHistoryQueryRepository pointHistoryQueryRepository;


    // 회원가입
    @Transactional
    public void registMember(SignupRequestDto requestDto){
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        if (memberRepository.existsByEmail(email)) {
            throw new ApiRequestException("이미 가입되어 있는 유저입니다");
        }

        // 회원 email(ID)중복확인
        Optional<Member> found = memberRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new ApiRequestException("중복된 사용자 email(ID)가 존재합니다.");
        }

        // 닉네임 중복확인
        existNickname(nickname);

        // 패스워드 인코딩
        String password= passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(password);

        Point point = new Point();
        pointRepository.save(point);

        Member member = Member.createMember(requestDto, point);
        memberRepository.save(member);
    }

    // 로그인
    @Transactional
    public MemberTokenResponseDto loginMember(LoginRequestDto requestDto){

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Member member = getMemberByEmail(requestDto.getEmail());

        // 자기가 참여한 챌린지에서 현재 진행중인리스트
        List<ChallengeRecord> targetList1 = challengeRecordQueryRepository.findAllByMemberAndStatus(member,1L);
        List<ChallengeRecord> targetList2 = challengeRecordQueryRepository.findAllByMemberAndStatus(member,2L);
        // 완료된 챌린지 리스트
        List<ChallengeRecord> completeList = challengeRecordQueryRepository.findAllByMemberAndProgress(member,3L);

        return createMemberTokenResponseDto(member, targetList1.size() + targetList2.size(), completeList.size());
    }

    // 새로고침
    @Transactional(readOnly = true)
    public ReloadResponseDto reload(String email){
        Member member = getMemberByEmail(email);

        // 자기가 참여한 챌린지에서 현재 진행중인리스트
        // 1번과 2번 상태가 겹치는거같음
        List<ChallengeRecord> targetList1 = challengeRecordQueryRepository.findAllByMemberAndStatus(member,1L);
        List<ChallengeRecord> targetList2 = challengeRecordQueryRepository.findAllByMemberAndStatus(member,2L);

        // 완료된 챌린지 리스트
        List<ChallengeRecord> completeList = challengeRecordQueryRepository.findAllByMemberAndProgress(member,3L);

        return createReloadResponseDto(member, targetList1.size() + targetList2.size(), completeList.size());
    }

    // 토큰 재발급

    // 마이 페이지 비밀번호 수정
    @Transactional
    public void updatePassword(PwUpdateRequestDto requestDto, String email){
        Member member = getMemberByEmail(email);

        if(!passwordEncoder.matches(requestDto.getCurrentPassword(), member.getPassword())){
            throw new ApiRequestException("현재 비밀번호가 일치하지 않습니다.");
        }

        String newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        requestDto.setNewPassword(newPassword);

        member.updatePassword(requestDto);
    }

    // 마이 페이지 (이미지 + 닉네임) 수정
    @Transactional
    public String updateProfile(ProfileUpdateRequestDto requestDto, String email){

        Member member = getMemberByEmail(email);

        // 닉네임 중복 처리 닉네임이 달라질경우만 중복확인체크 같은경우는 닉네임변경안하는경우
        if(!member.getNickname().equals(requestDto.getNickname())){
            existNickname(requestDto.getNickname());
        }

        return member.updateProfile(requestDto);
    }

    // 마이페이지 상세
    @Transactional(readOnly = true)
    public MyPageResponseDto getMyPage(String email){
        Member member = getMemberByEmail(email);

        MemberHistoryResponseDto history = getHistory(member);

        MyPageProceedResponseDto proceed = getProceed(member);
        MyPageScheduledResponseDto schedule = getScheduled(member);
        MyPageEndResponseDto end = getEnd(member);

        return createMyPageResponseDto(history, proceed, schedule, end);
    }

    //마이 페이지 히스토리
    public MemberHistoryResponseDto getHistory(Member member){

        // 포인트 번호
        int rank = 1;

        // 포인트 순위
        List<Point> pointList = pointRepository.findAllByOrderByAcquiredPointDesc();

        for (Point point : pointList) {
            if (member.getMemberId() == point.getPointId() && member.getPoint().getAcquiredPoint() == point.getAcquiredPoint()) {
                break;
            } else {
                rank++;
            }
        }

        // 1. 자기가 얻은 포인트 가져오기
        List<MemberHistoryDto> memberHistoryListPosting = pointHistoryQueryRepository.findHistoryPosting(member);
        List<MemberHistoryDto> memberHistoryListChallenge = pointHistoryQueryRepository.findHistoryChallenge(member);

        // 2. 포인트에 관한것만 빼기 원하는정보만 빼기 히스토리에관한것만 따로뺴고
        List<PointHistoryDto> pointHistoryListP = memberHistoryListPosting.stream()
                .map(memberHistory -> new PointHistoryDto(memberHistory))
                .collect(Collectors.toList());

        List<PointHistoryDto> pointHistoryListC = memberHistoryListChallenge.stream()
                .map(memberHistory -> new PointHistoryDto(memberHistory))
                .collect(Collectors.toList());

        return createMemberHistoryResponseDto(member, pointHistoryListP, pointHistoryListC, rank);
    }

    // 진행중인 챌린지
    public MyPageProceedResponseDto getProceed(Member member){
        //본인이 참여한 챌린지 기록리스트  1: 진행 예정, 2: 진행 중, 3 : 진행 완료
        List<ChallengeRecord> targetList = challengeRecordQueryRepository.findAllByMemberAndProgress(member,2L);

        // 본인이 참여한 챌린지 기록리스트 -> 챌린지 가져옴
        List<Challenge> proceeding = targetList.stream()
                .map(challengeRecord -> challengeRecord.getChallenge()).collect(Collectors.toList());


        // 본인이 참여한 챌린지 리스트 -> 가공
        List<ChallengeRecord> challengeRecordList= challengeRecordQueryRepository.findAllByChallenge(proceeding);
        List<ProceedResponseDto> proceedingResult = proceeding.stream()
                .map(challenge-> new ProceedResponseDto(challenge, challengeRecordList))
                .collect(Collectors.toList());

        return createMyPageProceedResponseDto(member,member.getPoint().getAcquiredPoint(), proceedingResult);
    }

    // 예정인 챌린지
    public MyPageScheduledResponseDto getScheduled(Member member){
        //본인이 참여한 챌린지 리스트  1: 진행 예정, 2: 진행 중, 3 : 진행 완료
        List<ChallengeRecord> targetList = challengeRecordQueryRepository.findAllByMemberAndProgress(member,1L);

        List<Challenge> scheduled = targetList.stream()
                .map(challengeRecord -> challengeRecord.getChallenge()).collect(Collectors.toList());

        List<ChallengeRecord> challengeRecordList= challengeRecordQueryRepository.findAllByChallenge(scheduled);
        List<ScheduledResponseDto> scheduledList = scheduled.stream()
                .map(challenge -> new ScheduledResponseDto(challenge, challengeRecordList))
                .collect(Collectors.toList());

        return createMyPageScheduledResponseDto(member,  scheduledList);
    }

    // 종료된 챌린지
    public MyPageEndResponseDto getEnd(Member member){
        //본인이 참여한 챌린지 리스트  1: 진행 예정, 2: 진행 중, 3 : 진행 완료
        List<ChallengeRecord> targetList = challengeRecordQueryRepository.findAllByMemberAndProgress(member,3L);

        List<Challenge> end = targetList.stream()
                .map(challengeRecord -> challengeRecord.getChallenge()).collect(Collectors.toList());

        List<ChallengeRecord> challengeRecordList= challengeRecordQueryRepository.findAllByChallenge(end);
        List<EndResponseDto> endList = end.stream()
                .map(challenge -> new EndResponseDto(challenge,challengeRecordList))
                .collect(Collectors.toList());

        return createMyPageEndResponseDto(member, endList);
    }

    // 닉네임 중복확인
    private void existNickname(String nickname){
        if(memberRepository.existsByNickname(nickname)){
            throw  new ApiRequestException("이미 존재하는 닉네임입니다.");
        }
    }

    // 이메일로 멤버 찾기
    private Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new ApiRequestException("멤버를 찾을수없는 이메일입니다.")
        );
    }
}

