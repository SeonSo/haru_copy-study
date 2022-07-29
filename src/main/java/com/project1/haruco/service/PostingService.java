package com.project1.haruco.service;

import com.project1.haruco.exception.ApiRequestException;
import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challenge.ChallengeRepository;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.member.MemberRepository;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.domain.posting.PostingRepository;
import com.project1.haruco.web.dto.request.posting.PostingCreateRequestDto;
import com.project1.haruco.web.dto.request.posting.PostingRequestDto;
import com.project1.haruco.web.dto.request.posting.PostingUpdateRequestDto;
import com.project1.haruco.web.dto.response.posting.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostingService {

    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;

    /**
     * 1.포스트 저장
     *
     */
    public Long createPosting(PostingCreateRequestDto postingRequestDto, String email) {
        Member member = getMemberById(postingRequestDto.getMemberId());
        Challenge challenge = getChallenge(postingRequestDto.getChallengeId());
        Posting posting = Posting.createPosting(postingRequestDto,member,challenge);

        // 포스팅 검사
        validatePosting();

        postingRepository.save(posting);

        return posting.getPostingId();
    }

    /**
     * 2.포스트 리스트
     *
     */
    @Transactional(readOnly = true)
    public List<PostingResponseDto> getPosting(int page, Long challengeId) {
        Challenge challenge = getChallenge(challengeId);
        Pageable pageable = PageRequest.of(page,6);

        List<Posting> postingList =
                postingRepository.findByChallengeAndPostingStatusTrueOrderByCreatedAtDesc(challenge,pageable);

        log.info("postingList : {} ",postingList);
        return postingList
                .stream()
                .map(PostingResponseDto::new)
                .collect(toList());
    }

    /**
     * 3.포스트 업데이트
     *
     */
    public Long updatePosting(Long postingId, String email, PostingUpdateRequestDto postingRequestDto) {

        Member member = getMemberByEmail(email);
        Posting posting = getPosting(postingId);

        // 작성자 검사
        validateMember(member,posting.getMember().getMemberId());

        // 포스팅 검사
        validatePosting();

        posting.updatePosting(postingRequestDto);
        return posting.getPostingId();
    }
    /**
     * 4.포스트 삭제
     *
     */
    public Long deletePosting(Long postingId, String email) {
        Member member = getMemberByEmail(email);
        Posting posting =getPosting(postingId);

        // 작성자 검사
        validateMember(member,posting.getMember().getMemberId());

        posting.deletePosting();
        return posting.getPostingId();


    }

    private Posting getPosting(Long postingId) {
        return postingRepository.findById(postingId)
                .orElseThrow(() -> new ApiRequestException("등록된 포스트가 없습니다."));
    }
    private Challenge getChallenge(Long challengeId) {
        log.info("getChallenge : {} ",challengeId);
        return challengeRepository.findChallengeStatusTrue(challengeId);
    }
    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiRequestException("등록된 멤버가 없습니다."));
    }
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("등록된 멤버가 없습니다."));
    }
    private void validateMember(Member member, Long memberId) {
        if (!memberId.equals(member.getMemberId())) {
            throw new ApiRequestException("해당 게시물에 대한 수정 권한이 없습니다.");
        }
    }
    private void validatePosting(){

        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);

        if(now.isAfter(today)){
            if(!now.isBefore(tomorrow)) {
                throw new ApiRequestException("수정 가능한 날이 아닙니다.");
            }
        }else{
            throw new ApiRequestException("챌린지 시작 후 등록할 수 있습니다!");
        }
    }

}
