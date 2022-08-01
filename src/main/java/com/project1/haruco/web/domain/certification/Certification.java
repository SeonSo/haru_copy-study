package com.project1.haruco.web.domain.certification;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.posting.Posting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Certification {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "certification_id")
    private Long certificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Certification(Member member, Posting posting) {
        this.member = member;
        this.posting = posting;
    }

    public static Certification createCertification(Member member, Posting posting) {
        Certification certification = Certification.builder()
                .member(member)
                .posting(posting)
                .build();

        posting.addCount();
        return certification;
    }

}
