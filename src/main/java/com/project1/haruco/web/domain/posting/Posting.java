package com.project1.haruco.web.domain.posting;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.posting.PostingRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@ToString

public class Posting extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long postingId;
    @Column
    private String postingId;
    @Column
    private String postingImg;
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
    @ManyToOne
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

    public static Posting createPosting(PostingRequestDto postingRequestDto, Member member, Challenge challenge) {
        return new Posting(
                postingRequestDto.getPostingImg(),
                postingRequestDto.getPostingContent(),
                member,
                challenge
        );
    }
    public void updatePosting(PostingRequestDto postingRequestDto) {
        this.postingImg = postingRequestDto.getPostingImg();
        this.postingContent = postingRequestDto.getPostingContent();

    }
    public void deletePosting() {
        this.postingStatus =false;
    }
    public void addCount() {
        this.postingCount += 1;
    }

}
