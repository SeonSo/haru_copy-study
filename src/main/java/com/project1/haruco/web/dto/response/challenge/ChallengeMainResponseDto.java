package com.project1.haruco.web.dto.response.challenge;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class ChallengeMainResponseDto {
    private List<ChallengeSourceResponseDto> slider = new ArrayList<>();
    private List<ChallengeSourceResponseDto> popular = new ArrayList<>();
    private List<ChallengeSourceResponseDto> exercise = new ArrayList<>();
    private List<ChallengeSourceResponseDto> livinghabits = new ArrayList<>();
    private List<ChallengeSourceResponseDto> nodrinknosmoke = new ArrayList<>();

    @Builder
    public ChallengeMainResponseDto(List<ChallengeSourceResponseDto> slider,
                                    List<ChallengeSourceResponseDto> popular,
                                    List<ChallengeSourceResponseDto> exercise,
                                    List<ChallengeSourceResponseDto> livinghabits,
                                    List<ChallengeSourceResponseDto> nodrinknosmoke) {
        this.slider = slider;
        this.popular = popular;
        this.exercise = exercise;
        this.livinghabits = livinghabits;
        this.nodrinknosmoke = nodrinknosmoke;
    }

    public static ChallengeMainResponseDto createChallengeMainResponseDto(List<ChallengeSourceResponseDto> slider,
                                                                          List<ChallengeSourceResponseDto> popular,
                                                                          List<ChallengeSourceResponseDto> exercise,
                                                                          List<ChallengeSourceResponseDto> livinghabits,
                                                                          List<ChallengeSourceResponseDto> nodrinknosmoke) {
        return ChallengeMainResponseDto.builder()
                .slider(slider)
                .popular(popular)
                .exercise(exercise)
                .livinghabits(livinghabits)
                .nodrinknosmoke(nodrinknosmoke)
                .build();
    }
}
