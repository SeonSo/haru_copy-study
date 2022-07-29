package com.project1.haruco.web.dto.response.challenge;

<<<<<<< HEAD
=======
import lombok.Builder;
>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class ChallengeListResponseDto {
<<<<<<< HEAD
    private final List<ChallengeResponseDto> result = new ArrayList<>();

    public void addResult(ChallengeResponseDto responseDto) {
        this.result.add(responseDto);
    }
}
=======
    private List<ChallengeResponseDto> challengeList = new ArrayList<>();
    private boolean hasNext;

    @Builder
    public ChallengeListResponseDto(List<ChallengeResponseDto> challengeList, boolean hasNext){
        this.challengeList = challengeList;
        this.hasNext = hasNext;
    }

    public static ChallengeListResponseDto createChallengeListDto(List<ChallengeResponseDto> dto, boolean hasNext){
        return ChallengeListResponseDto.builder()
                .challengeList(dto)
                .hasNext(hasNext)
                .build();
    }
}
>>>>>>> 3308a580f000088f67fcc06991bf72eac2e3132f
