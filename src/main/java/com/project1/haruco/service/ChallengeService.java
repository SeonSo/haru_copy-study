package com.project1.haruco.service;

import com.project1.haruco.exception.ApiRequestException;
import com.project1.haruco.web.domain.challenge.CategoryName;
import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challenge.ChallengeQueryRepository;
import com.project1.haruco.web.domain.challenge.ChallengeRepository;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecordQueryRepository;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecordRepository;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.member.MemberRepository;
import com.project1.haruco.web.dto.request.challenge.ChallengeRequestDto;
import com.project1.haruco.web.dto.request.challenge.PutChallengeRequestDto;
import com.project1.haruco.web.dto.response.challenge.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.project1.haruco.web.domain.challenge.CategoryName.*;
import static com.project1.haruco.web.domain.challenge.Challenge.createChallenge;
import static com.project1.haruco.web.domain.challengeRecord.ChallengeRecord.createChallengeRecord;
import static com.project1.haruco.web.dto.response.challenge.ChallengeDetailResponseDto.createChallengeDetailResponseDto;
import static com.project1.haruco.web.dto.response.challenge.ChallengeMainResponseDto.createChallengeMainResponseDto;
import static com.project1.haruco.web.dto.response.challenge.ChallengeSourceResponseDto.createChallengeSourceResponseDto;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeQueryRepository challengeQueryRepository;
    private final ChallengeRecordRepository challengeRecordRepository;
    private final ChallengeRecordQueryRepository challengeRecordQueryRepository;
    private final MemberRepository memberRepository;

    //????????? ??????
    public ChallengeDetailResponseDto getChallengeDetail(Long challengeId) {
        Challenge challenge = challengeChecker(challengeId);
        List<ChallengeDetailResponseDtoMemberDto> memberList = challengeRecordQueryRepository.findAllByChallengeId(challengeId);
        return createChallengeDetailResponseDto(challenge, memberList);
    }

    //????????? ??????
    @Transactional
    public Long postChallenge(ChallengeRequestDto requestDto, String email) {
        Member member = memberChecker(email);
        createChallengeException(requestDto, member);

        //????????? ??????
        Challenge challenge = createChallenge(requestDto, member);
        challengeRepository.save(challenge);

        Long challengeId = challenge.getChallengeId();
        ChallengeRecord challengeRecord = createChallengeRecord(challenge, member);
        challengeRecordRepository.save(challengeRecord);

        return challengeId;
    }

    //????????? ??????
    @Transactional
    public void deleteChallenge(Long challengeId, String username) {
        Challenge challenge = challengeChecker(challengeId);
        deleteChallengeException(username, challenge);

        List<ChallengeRecord> recordList = challengeRecordRepository.findAllByChallengeAndChallengeRecordStatusTrue(challenge);
        recordList.forEach(ChallengeRecord::setStatusFalse);
    }

    //????????? ??????
    @Transactional
    public void putChallenge(PutChallengeRequestDto requestDto, String email) {
        Member member = memberChecker(email);
        Challenge challenge = challengeChecker(requestDto.getChallengeId());
        putChallengeException(member, challenge);
        challenge.putChallenge(requestDto);
    }

    //?????? ?????????
    public ChallengeMainResponseDto getMainPage() {
        List<ChallengeRecord> records = challengeRecordQueryRepository.findAllByStatusTrue();

        return createChallengeMainResponseDto(
                sliderCollector(records),
                popularCollector(records),
                categoryCollector(EXERCISE, records),
                categoryCollector(LIVINGHABITS, records),
                categoryCollector(NODRINKNOSMOKE, records));
    }

    private List<ChallengeSourceResponseDto> sliderCollector(List<ChallengeRecord> records) {
        List<Challenge> officialList = challengeQueryRepository.findAllByOfficialChallenge();

        return officialList
                .stream()
                .map(c -> createChallengeSourceResponseDto(c, records))
                .collect(Collectors.toList());
    }

    private List<ChallengeSourceResponseDto> popularCollector(List<ChallengeRecord> records) {
        final int POPULAR_SIZE = 4;

        List<ChallengeRecord> popularRecords = challengeRecordQueryRepository
                .findAllPopular(PageRequest.of(0, POPULAR_SIZE));
        return popularRecords
                .stream()
                .map(r -> (createChallengeSourceResponseDto(r.getChallenge(), records)))
                .collect(Collectors.toList());
    }

    private List<ChallengeSourceResponseDto> categoryCollector(CategoryName category, List<ChallengeRecord> records) {
        final int CATEGORY_SIZE = 3;

        List<Challenge> challenges = records
                .stream()
                .map(ChallengeRecord::getChallenge)
                .filter(challenge -> challenge.getCategoryName().equals(category))
                .distinct()
                .limit(CATEGORY_SIZE)
                .collect(Collectors.toList());

        return challenges
                .stream()
                .map(c -> createChallengeSourceResponseDto(c, records))
                .collect(Collectors.toList());
    }

    private Challenge challengeChecker(Long challengeId) {
        return challengeQueryRepository.findById(challengeId)
                .orElseThrow(() -> new ApiRequestException("???????????? ?????? ??????????????????"));
    }

    private void putChallengeException(Member member, Challenge challenge) {
        if (!challenge.getMember().equals(member)) {
            throw new ApiRequestException("?????? ????????? ?????? ??????????????????.");
        }
        if (!challenge.getChallengeProgress().equals(1L)) {
            throw new ApiRequestException("?????? ??????????????? ????????? ??????????????????.");
        }
        if (!challenge.isChallengeStatus()) {
            throw new ApiRequestException("????????? ??????????????????.");
        }
    }

    private Member memberChecker(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("???????????? ?????? ???????????????."));
    }

    private void deleteChallengeException(String username, Challenge challenge) {
        if (!challenge.getMember().getEmail().equals(username)) {
            throw new ApiRequestException("???????????? ????????????.");
        }
        if (LocalDateTime.now().isBefore(challenge.getChallengeStartDate())) {
            challenge.setChallengeStatusFalse();
            challenge.updateChallengeProgress(3L);
        } else {
            throw new ApiRequestException("?????? ????????? ???????????? ????????? ??? ????????????.");
        }
    }

    private void createChallengeException(ChallengeRequestDto requestDto, Member member) {
        List<ChallengeRecord> recordList = challengeRecordQueryRepository.findAllByMember(member);
        if (recordList.size() >= 10) {
            throw new ApiRequestException("?????? 10?????? ???????????? ???????????? ?????? ???????????????.");
        }
        if (requestDto.getChallengePassword().length() < 4) {
            if (!requestDto.getChallengePassword().equals("")) {
                throw new ApiRequestException("??????????????? 4?????? ???????????? ?????????????????????.");
            }
        }
        if (!StringUtils.hasText(requestDto.getChallengeTitle())) {
            throw new ApiRequestException("????????? ????????????.");
        }
        if (!StringUtils.hasText(requestDto.getChallengeContent())) {
            throw new ApiRequestException("????????? ????????????.");
        }
        if (!StringUtils.hasText(requestDto.getChallengeImgUrl())) {
            throw new ApiRequestException("????????? ???????????? ????????????.");
        }
        if (!StringUtils.hasText(requestDto.getChallengeGood())) {
            throw new ApiRequestException("?????? ????????? ????????????.");
        }
        if (!StringUtils.hasText(requestDto.getChallengeBad())) {
            throw new ApiRequestException("?????? ????????? ????????????.");
        }
    }
}
