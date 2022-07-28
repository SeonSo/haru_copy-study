package com.project1.haruco.web.domain.point;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project1.haruco.web.domain.certification.Certification;
import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Point extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pointId;

    @Column
    private Long acquiredPoint;

    // 애가관계의주인임 관계의주인쪽이 관계가설정되는것
    @ManyToOne
    @JsonIgnore
    private Member member;

    @ManyToOne
    private Certification certification;

    public Point(Member member){
        this.member = member;
        this.acquiredPoint = 0L;
    }

    public Point(Member member, Certification certification) {
        this.member = member;
        this.certification =certification;
        this.acquiredPoint = 1L;
    }

}
