package com.project1.haruco.web.domain.point;

import com.project1.haruco.web.domain.certification.Certification;
import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.swing.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Point extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column
    private Long acquiredPoint;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Certification certification;

    public Point(Member member){
        this.member = member;
        this.acquiredPoint = 0L;
    }

    public Point(Member member, Certification certification){
        this.member = member;
        this.certification = certification;
        this.acquiredPoint = 1L;
    }
}
