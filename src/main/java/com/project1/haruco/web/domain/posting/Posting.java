package com.project1.haruco.web.domain.posting;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.dto.request.posting.PostingCreateRequestDto;
import com.project1.haruco.web.dto.request.posting.PostingUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(indexes = {@Index(name = "idx_modify_status", columnList = "postingModifyOk"),
        @Index(name = "idx_approval_status", columnList = "postingApproval"),
        @Index(name = "idx_posting_status", columnList = "postingStatus")})
public class Posting extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "posting_Id")
    private Long postingId;

    @Column(columnDefinition="TEXT")
    private String postingImg;

    @Column(columnDefinition="TEXT")
    private String postingContent;

    @Column
    private boolean postingStatus;

    @Column
    private boolean postingApproval;

    @Column
    private boolean postingModifyOk;

    @Column
    private boolean postingPoint;

    @Column
    private Long postingCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Builder
    public Posting(String postingImg, String postingContent, Member member, Challenge challenge) {
        this.postingImg = postingImg;
        this.postingContent =postingContent;
        this.postingStatus = true;
        this.postingApproval=false;
        this.postingPoint=false;
        this.postingModifyOk=true;
        this.postingCount=0L;
        this.member =member;
        this.challenge=challenge;
    }

    //==생성 메서드==//
    public static Posting createPosting(PostingCreateRequestDto postingRequestDto, Member member, Challenge challenge) {
        return Posting.builder()
                .postingImg(postingRequestDto.getPostingImg())
                .postingContent(postingRequestDto.getPostingContent())
                .member(member)
                .challenge(challenge)
                .build();
    }

    //== 비지니스 로직 ==//
    public void updatePosting(PostingUpdateRequestDto postingRequestDto) {
        this.postingImg = postingRequestDto.getPostingImg();
        this.postingContent = postingRequestDto.getPostingContent();

    }
    public void deletePosting() {
        this.postingStatus =false;
    }

    public void addCount() {
        this.postingCount += 1;
    }

    public void updateApproval(boolean isApproval) {
        this.postingApproval = isApproval;
    }

    public void updatePoint() {
        this.postingPoint = true;
    }
}
