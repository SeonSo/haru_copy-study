package com.project1.haruco.web.domain.member;

import com.project1.haruco.web.domain.certification.Certification;
import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.point.Point;
import com.project1.haruco.web.dto.request.mypage.MyPageRequestDto;
import com.project1.haruco.web.dto.request.signup.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Entity
@Getter
public class Member extends Timestamped implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="member_id")
    private Long memberId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    @Column
    private String profileImg;
    @Column
    private Long memberStatus;

    @OneToMany(mappedBy = "member")
    private List<Point> points = new ArrayList<>();

    public void add(Point point){
        point.setMember(this);
        this.points.add(point);
    }
    public Member(SignupRequestDto requestDto){
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.profileImg = requestDto.getProfileImg();
        this.memberStatus = 1L;
        this.role = MemberRole.MEMBER;
    }
    public Member(String email, String password, String nickname, String profileImg){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.memberStatus = 1L;
        this.role = MemberRole.MEMBER;
    }

    public void update(MyPageRequestDto requestDto){
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.profileImg = requestDto.getProfileImg();
    }

    public Point updatePoint(Member member, Certification certification) {

        return new Point(member,certification);
    }
}
