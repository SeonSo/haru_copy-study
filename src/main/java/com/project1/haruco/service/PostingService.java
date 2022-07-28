package com.project1.haruco.service;

import com.project1.haruco.exception.ApiRequestException;
import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challenge.ChallengeRepository;
import com.project1.haruco.web.domain.member.MemberRepository;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.domain.posting.PostingRepository;
import com.project1.haruco.web.dto.request.posting.PostingRequestDto;
import com.project1.haruco.web.dto.response.posting.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostingService {

    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;

    //저장
    public Long createPosting(PostingRequestDto postingRequestDto){
        Member member = getMemberById(postingRequestDto.getMemberId());
        Challenge Challenge = getChallenge(postingRequestDto.getChallengeId());
        Posting posting = Posting.createPosting(postingRequestDto, member, challenge);

        vaildatePosting();
        postingRepository.save(posting);

        return posting.getPostingId();
    }

    //리스트
    @Transactional(readOnly = true)
    public List<PostingResponseDto> getPosting(int page, Long challengeId){
        Challenge challenge = getChallenge(challengeId);
        Pageable pageable = PageRequest.of(page,6);

        List<Posting> postingList = postingRepository.findByChallengeAndPostingStatusTrueOrderByCreatedAtDesc(challenge, pageable);
        log.info("postingList : {} ", postingList);
        return challengeRepository.findById(postingRequestDto.getChallenge().getId()).orElseThrow(() -> new ApiRequestException("등록된 챌린지가 없습니다."));
    }

    private Member getMember(PostingRequestDto postingRequestDto){
        return memberRepository.findById(postingRequestDto.getMember().getId()).orElseThrow(() -> new ApiRequestException("등록된 멤버가 없습니다."));
    }
}



