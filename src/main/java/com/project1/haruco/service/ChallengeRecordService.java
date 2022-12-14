package com.project1.haruco.service;

import com.project1.haruco.exception.ApiRequestException;
import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challenge.ChallengeRepository;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecord;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecordQueryRepository;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecordRepository;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.member.MemberRepository;
import com.project1.haruco.web.dto.request.challengeRecord.ChallengeRecordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project1.haruco.web.domain.challenge.CategoryName.OFFICIAL;

@Service
@RequiredArgsConstructor
public class ChallengeRecordService {

    private final ChallengeRecordRepository challengeRecordRepository;
    private final ChallengeRecordQueryRepository challengeRecordQueryRepository;
    private final ChallengeRepository challengeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void requestChallenge(ChallengeRecordRequestDto requestDto, String email) {

        Challenge challenge = ChallengeChecker(requestDto.getChallengeId());
        Member member = MemberChecker(email);

        requestChallengeException(challenge, member);

        ChallengeRecord record = ChallengeRecord.createChallengeRecord(challenge, member);
        challengeRecordRepository.save(record);
    }

    @Transactional
    public void giveUpChallenge(Long challengeId, String email) {
        Challenge challenge = ChallengeChecker(challengeId);
        Member member = MemberChecker(email);

        ChallengeRecord record = challengeRecordRepository
                .findByChallengeAndMemberAndChallengeRecordStatusTrue(challenge, member);
        record.setStatusFalse();
    }

    private Challenge ChallengeChecker(Long challengeId){
        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ApiRequestException("???????????? ?????? ??????????????????."));
    }

    private Member MemberChecker(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("???????????? ?????? ???????????????."));
    }

    private void requestChallengeException(Challenge challenge, Member member){
        List<ChallengeRecord> recordList = challengeRecordQueryRepository.findAllByMember(member);
        if (recordList.size() >= 10) {
            throw new ApiRequestException("?????? 10?????? ???????????? ???????????? ?????? ???????????????.");
        }
        if (!challenge.getCategoryName().equals(OFFICIAL) &&
                challengeRecordQueryRepository.countByChallenge(challenge) >= 10) {
            throw new ApiRequestException("???????????? 10???????????? ?????? ???????????????.");
        }
        if (recordList.stream().anyMatch(r -> r.getChallenge().equals(challenge))) {
            throw new ApiRequestException("?????? ?????? ???????????? ???????????? ?????? ???????????????.");
        }
    }
}
