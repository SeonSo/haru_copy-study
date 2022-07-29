package com.project1.haruco.web.dto.request.mypage;

import com.project1.haruco.exception.ApiRequestException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PwUpdateRequestDto {
    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;

    public PwUpdateRequestDto(String currentPassword, String newPassword, String newPasswordConfirm){
        if(currentPassword.isEmpty()){
            throw new ApiRequestException("현재 비밀번호를 입력해주세요.");
        } if(newPassword.isEmpty() || newPasswordConfirm.isEmpty()){
            throw new ApiRequestException("새 비밀번호를 입력하세요.");
        } if(currentPassword.equals(newPassword)){
            throw new ApiRequestException(("현재 비밀번호와 새 비밀번호가 같습니다."));
        } if(!newPassword.equals(newPasswordConfirm)){
            throw new ApiRequestException(("비밀번호가 일치하지 않습니다."));
        } if(newPassword.length() < 4 || newPassword.length() > 20){
            throw new ApiRequestException("변경할 비밀번호는  4~20자리를 사용해야 합니다.");
        }

        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }
}
