package com.project1.haruco.web.domain.certification;

import com.project1.haruco.web.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.*;
import java.lang.reflect.Member;

@Entity
@Getter
@NoArgsConstructor
public class Certification {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long certificationId;

    @ManyToOne
    private Posting posting;

    @ManyToOne
    private Member member;

    public Certification(Member member, Posting posting) {
        this.member=member;
        this.posting=posting;
    }

    public static Certification createCertification(Member member, Posting posting) {
        Certification certification = new Certification(member, posting);
        posting.addCount();
        return certification;
    }
}
