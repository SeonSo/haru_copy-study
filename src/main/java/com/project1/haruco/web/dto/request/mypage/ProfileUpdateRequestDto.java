package com.project1.haruco.web.dto.request.mypage;

import com.project1.haruco.exception.ApiRequestException;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class ProfileUpdateRequestDto {

    private String nickname;
    private String profileImg;

     public ProfileUpdateRequestDto(String nickname, String profileImg){
         if(nickname.trim().contains(" ")){
             throw new ApiRequestException(("공백을 포함할 수 없습니다."));
         } if(nickname == null || nickname.isEmpty()){
             throw new ApiRequestException("닉네임을 입력해주세요.");
         } if(nickname.length() > 20){
             throw new ApiRequestException("닉네임은 20글자를 초과할 수 없습니다.");
         }

         if(profileImg == null || profileImg.isEmpty()){
             throw new ApiRequestException("프로필이미지를 넣어주세요.");
         }

         this.nickname = nickname;
         this.profileImg = profileImg;
     }
}
