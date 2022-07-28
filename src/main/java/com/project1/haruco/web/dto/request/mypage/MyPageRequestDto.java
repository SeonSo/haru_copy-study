package com.project1.haruco.web.dto.request.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MyPageRequestDto {
    private String nickname;
    private String password;
    private String profileImg;
}
