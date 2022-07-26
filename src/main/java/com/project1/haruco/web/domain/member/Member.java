package com.project1.haruco.web.domain.member;

import com.project1.haruco.web.domain.certification.Certification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;

@NoArgsConstructor
@Entity
@Getter
public class Member extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long memberId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    @Enumerated(value = EnumType.STRING)
    private MenberRole role;

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
        this.memverStatus = 1L;
        this.role = MemberRole.MEMBER;
    }

    public void update(MyPageRequestDto requestDto){
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.profileImg = requestDto.getProfileImg();
    }

    public Point updatePoint(Member member, Certification certification){
        return new Point(member, certification);
    }
}
