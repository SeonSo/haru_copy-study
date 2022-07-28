package com.project1.haruco.service;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.challenge.ChallengeRepository;
import com.project1.haruco.web.domain.posting.Posting;
import com.project1.haruco.web.domain.posting.PostingRepository;
import com.project1.haruco.web.dto.response.posting.PostingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;


@Service
@RequiredArgsConstructor

public class PostingService {

    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;

    public Long createPosting(PostingRequestDto postingRequestDto){
        Member member = getMemberById(postingRequestDto.getMemberId());
        Challenge challenge = getChallenge(postingRequestDto.getChallengeId);
        Posting posting = Posting.createPosting(postingRequestDto,member,challenge);
        validatePosting();
        postingRepository.save(posting);

        return posting.getPostingId();
    }
    public List<PostingResponseDto> getPosting(int page, Long challengeId){
        Challenge challenge = getChallenge(challengeId);
        Pageable pageable = PageRequest.of(page,6);
        List<Posting> postingList = postingRepository.findByChallengeAndPostingStatusTrueOrderByCreateAtDesc(challenge,pageable);
        log.info("postingList : {} ", postingList);
        return postingList
                .stream()
                .map(PostingResponseDto:new)

    }
    public Long updatePosting(Long postingId,String email,PostingRequestDto postingRequestDto){
        Member member = getMemberByEmail(email);
        Posting posting = getPosting(postingId);
        validateMember(member,posting.getMember().getMemberId());
        validatePosting();
        posting.updatePosting(postingRequestDto);
        return posting.getPostingId();
    }
    private Challenge getChallenge(PostingRequestDto postingRequestDto){
        return challengeRepository.findById(postingRequestDto.getChallenge().getId()).orElseThrow(() -> new ApiRequestException("등록된 챌린지가 없습니다."));
    }

    private Member getMember(PostingRequestDto postingRequestDto){
        return memberRepository.findById(postingRequestDto.getMember().getId()).orElseThrow(() -> new ApiRequestException("등록된 멤버가 없습니다."));
    }
}



