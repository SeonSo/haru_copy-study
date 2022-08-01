package com.project1.haruco.web.domain.member;

import com.project1.haruco.web.domain.commom.Timestamped;
import com.project1.haruco.web.domain.point.Point;
import com.project1.haruco.web.dto.request.mypage.ProfileUpdateRequestDto;
import com.project1.haruco.web.dto.request.mypage.PwUpdateRequestDto;
import com.project1.haruco.web.dto.request.signup.SignupRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Slf4j
@NoArgsConstructor
@Entity
@Getter
@Table(indexes = {@Index(name = "idx_member_status", columnList = "member_status")})
public class Member extends Timestamped implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "member_id")
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

    @Column(columnDefinition = "TEXT")
    private String profileImg;

    @Column(name = "member_status")
    private Long memberStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POINT_ID")
    private Point point;

    @Builder
    public Member(Long memberId, String email, String password,
                  String nickname, MemberRole role, String profileImg,
                  Long memberStatus, Point point){
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = MemberRole.MEMBER;
        this.profileImg = profileImg;
        this.memberStatus = 1L;
        this.point = point;
    }

    public static Member createMember(SignupRequestDto requestDto,
                                      Point point){
        return Member.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .nickname(requestDto.getNickname())
                .profileImg(requestDto.getProfileImg())
                .point(point)
                .build();
    }

    // 마이페이지 수정
    public void updatePassword(PwUpdateRequestDto requestDto){
        this.password = requestDto.getNewPassword();
    }

    public String updateProfile(ProfileUpdateRequestDto requestDto){
        this.nickname = requestDto.getNickname();
        this.profileImg = requestDto.getProfileImg();
        return this.profileImg;
    }

    public Long updatePoint(Long getPoint) {
        Long before = this.getPoint().getAcquiredPoint();
        Long result = before + getPoint;
        this.getPoint().setAcquiredPoint(result);
        return result;
    }
}

