package com.project1.haruco.service;

import com.project1.haruco.exception.ApiRequestException;
import com.project1.haruco.web.domain.certification.Certification;
import com.project1.haruco.web.domain.certification.CertificationRepository;
import com.project1.haruco.web.domain.challengeRecord.ChallengeRecordRepository;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.member.MemberRepository;
import com.project1.haruco.web.domain.point.Point;
import com.project1.haruco.web.domain.point.PointRepository;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.domain.posting.PostingRepository;
import com.project1.haruco.web.dto.request.certification.CertificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;
    private final ChallengeRecordRepository challengeRecordRepository;

    @Transactional
    public Long createCertification(CertificationRequestDto certificationRequestDto, UserDetails userDetails) {
        Member member = getMemberByEmail(userDetails.getUsername());

        int count = getCount(posting);

        duplicate(posting, member);

        Certification certification = Certification.createCertification(member,posting);

        checkMemberCountAndAddPoint(posting, member, count, certification);

        certificationRepository.save(certification);

        return certification.gerCertificationId();
    }

    private void checkMemberCountAndAddPoint (Posting posting, Member member, int count, Certification certification){
        if(count/2 < posting.getPostingCount()){
            Point point = member.updatePoint(member, certification);
        }
    }

    private void duplicate(Posting posting, Member member){
        if(certificationRepository.existsByPostingAndMember(posting, member)){
            throw new ApiRequestException("이미 인증된 게시물입니다.")
        }
    }

    private int getCount(Posting posing){
        return challengeRecordRepository.findAllByChallenge(posting.getChallenge()).size();
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("등록된 유저가 없습니다."));
    }

    private Posting getPosting(Long postingId) {
        return postingRepository.findById(postingId)
                .orElseId(() -> new ApiRequestException("등록된 포스트가 없습니다."))
    }
}
