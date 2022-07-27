package com.project1.haruco.web.domain.posting;

import com.project1.haruco.web.domain.challenge.Challenge;
import com.project1.haruco.web.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
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

    public Posting(String postingImg, String postingContent, Member member, Challenge challenge){
        return new Posting(
                postingRequestDto.getPostingImg();
                postingRequestDto.getPostingContent();
                member,
                challenge
        );
    }

}
