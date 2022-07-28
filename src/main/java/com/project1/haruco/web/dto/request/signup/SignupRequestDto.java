package com.project1.haruco.web.dto.request.signup;

import com.project1.haruco.exception.ApiRequestException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String nickname;
    private String password;
    private String passwordConfirm;
    private String profileImg;
    private Long memberStatus;

    public SignupRequestDto(String email, String nickname,
                            String password, String passwordConfirm,
                            String profileImg){
        if(email.isEmpty()){
            throw new ApiRequestException("email(ID)를 입력해주세요.");
        } if(!email.matches(("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"))){
            throw new ApiRequestException("올바른 이메일 형식이 아닙니다.");
        }

        if(password.isEmpty() || passwordConfirm.isEmpty()){
            throw new ApiRequestException("패스워드를 입력해주세요.");
        } if(password.length() < 4 || password.length() >20){
            throw new ApiRequestException("비밀번호는 4~20자리만 사용 가능합니다.");
        } if(!(password.equals(passwordConfirm))){
            throw new ApiRequestException("확인 비밀번호가 틀렸습니다.");
        }

        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.profileImg = profileImg;
        this.memberStatus = 1L;
    }
}
