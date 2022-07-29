package com.project1.haruco.web.dto.request.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Getter
public class MyPageRequestDto {

    private String password;
    private String nickname;
    private String profileImg;
}
