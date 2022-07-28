package com.project1.haruco.web.dto.request.Token;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
