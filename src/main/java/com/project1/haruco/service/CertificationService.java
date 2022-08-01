package com.project1.haruco.service;

import com.project1.haruco.exception.ApiRequestException;
import com.project1.haruco.web.domain.certification.Certification;
import com.project1.haruco.web.domain.certification.CertificationRepository;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.member.MemberRepository;
import com.project1.haruco.web.domain.pointHistory.PointHistory;
import com.project1.haruco.web.domain.pointHistory.PointHistoryRepository;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.domain.posting.PostingRepository;
import com.project1.haruco.web.dto.request.certification.CertificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;
    private final PointHistoryRepository pointHistoryRepository;


    @Transactional
    public Long createCertification(CertificationRequestDto certificationRequestDto, UserDetails userDetails) {
        Posting posting = getPosting(certificationRequestDto.getPostingId());
        Member member = getMemberByEmail(userDetails.getUsername());

        Long memberCount = certificationRequestDto.getTotalNumber();

        // 인증 했는지 여부 확인
        duplicateCertification(posting, member);

        Certification certification = Certification.createCertification(member, posting);

        certificationRepository.save(certification);

        checkMemberCountAndAddPoint(posting, memberCount);

        return posting.getPostingId();
    }


    private void checkMemberCountAndAddPoint (Posting posting, Long memberCount) {
        if(memberCount / 2 <= posting.getPostingCount()){
            if(!posting.isPostingApproval()){
                PointHistory pointHistory = PointHistory.createPostingPointHistory(1L, posting);
                pointHistoryRepository.save(pointHistory);
                posting.getMember().updatePoint(1L);
                posting.updateApproval(true);
                posting.updatePoint();
            }
        }
    }

    private void duplicateCertification(Posting posting,Member member){
        if(certificationRepository.existsByPostingAndMember(posting, member)){
            throw new ApiRequestException("이미 인증한 게시물입니다");
        }
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("등록된 유저가 없습니다."));
    }
    private Posting getPosting(Long postingId) {
        return postingRepository.findById(postingId)
                .orElseThrow(() -> new ApiRequestException("등록된 포스트가 없습니다."));
    }
}
