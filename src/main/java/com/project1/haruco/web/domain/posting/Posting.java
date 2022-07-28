package com.project1.haruco.web.domain.posting;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.request.posting.PostingRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Getter
@Entity
public class Posting extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_id")
    private Long postingId;

    @Column(columnDefinition = "TEXT")
    private String postingImg;

    @Column(columnDefinition = "TEXT")
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

    @ManyToOne
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Challenge challenge;

    @Builder
    public Posting(String postingImg, String postingContent, Member member, Challenge challenge){
        this.postingImg = postingImg;
        this.postingContent = postingContent;
        this.postingStatus = true;
        this.postingApproval = false;
        this.postingPoint = false;
        this.postingModifyOk = true;
        this.member = member;
        this.challenge = this.challenge;
    }

    public static Posting createPosting(PostingRequestDto postingRequestDto, Member member, Challenge challenge){
        return new Posting(
                postingRequestDto.getPostingImg(),
                postingRequestDto.getPostingContent(),
                member,
                challenge
        );
    }

    public void updatePosting(PostingRequestDto postingRequestDto){
        this.postingImg = postingRequestDto.getPostingImg();
        this.postingContent = postingRequestDto.getPostingContent();
    }

    public void deletePosting(){
        this.postingStatus = false;
    }

    public void addCount(){
        this.postingCount += 1;
    }

    public void updateApproval(boolean isApproval){
        this.postingApproval = isApproval;
    }

    public void updatePoint(){
        this.postingPoint = true;
    }

}
