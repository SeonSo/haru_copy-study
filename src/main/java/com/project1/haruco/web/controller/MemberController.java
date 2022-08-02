package com.project1.haruco.web.controller;

import com.project1.haruco.service.MemberService;
import com.project1.haruco.web.dto.request.login.LoginRequestDto;
import com.project1.haruco.web.dto.request.mypage.ProfileUpdateRequestDto;
import com.project1.haruco.web.dto.request.mypage.PwUpdateRequestDto;
import com.project1.haruco.web.dto.request.signup.SignupRequestDto;
import com.project1.haruco.web.dto.request.token.TokenRequestDto;
import com.project1.haruco.web.dto.response.member.MemberTokenResponseDto;
import com.project1.haruco.web.dto.response.member.reload.ReloadResponseDto;
import com.project1.haruco.web.dto.response.mypage.MyPageResponseDto;
import com.project1.haruco.web.dto.response.token.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public void registerUser(@RequestBody SignupRequestDto signupRequestDto){
        memberService.registMember(signupRequestDto);
    }

    // 로그인
    @PostMapping("/login")
    public MemberTokenResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return memberService.loginMember(loginRequestDto);
    }

    //새로고침
    @GetMapping("/reload")
    public ReloadResponseDto reload(@AuthenticationPrincipal UserDetails userDetails) {
        return memberService.reload(userDetails.getUsername());
    }

    // 재발급
    @PostMapping("/reissue")
    public MemberTokenResponseDto reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return memberService.reissue(tokenRequestDto);
    }


    // 마이 페이지 상세
    @GetMapping("/mypage")
    public MyPageResponseDto getMyPage(@AuthenticationPrincipal UserDetails userDetails){
        return memberService.getMyPage(userDetails.getUsername());
    }

    // 마이 페이지 수정(프로필 + 닉네임)
    @PutMapping("/mypage/profile")
    public String updateMyPageInfoProfile(@RequestBody ProfileUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails){
        return memberService.updateProfile(requestDto, userDetails.getUsername());
    }

    // 마이 페이지 수정(비밀번호)
    @PutMapping("/mypage/password")
    public void updateMyPageInfoPassword(@RequestBody PwUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails){
        memberService.updatePassword(requestDto, userDetails.getUsername());
    }
}
