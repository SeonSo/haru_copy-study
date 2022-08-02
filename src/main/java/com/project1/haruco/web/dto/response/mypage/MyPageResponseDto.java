package com.project1.haruco.web.dto.response.mypage;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.dto.response.mypage.end.MyPageEndResponseDto;
import com.project1.haruco.web.dto.response.mypage.history.MemberHistoryResponseDto;
import com.project1.haruco.web.dto.response.mypage.proceed.MyPageProceedResponseDto;
import com.project1.haruco.web.dto.response.mypage.scheduled.MyPageScheduledResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MyPageResponseDto {

    private MemberHistoryResponseDto memberHistoryResponseDto; // 히스토리
    private MyPageProceedResponseDto myPageProceedResponseDto; // 진행중인 챌린지
    private MyPageScheduledResponseDto myPageScheduledResponseDto; // 예정인 챌린지
    private MyPageEndResponseDto myPageEndResponseDto; // 종료된 챌린지

    public MyPageResponseDto(MemberHistoryResponseDto history, MyPageProceedResponseDto proceed,
                             MyPageScheduledResponseDto schedule, MyPageEndResponseDto end){
        this.memberHistoryResponseDto = history;
        this.myPageProceedResponseDto = proceed;
        this.myPageScheduledResponseDto = schedule;
        this.myPageEndResponseDto = end;
    }

    @Builder
    public static MyPageResponseDto createMyPageResponseDto(MemberHistoryResponseDto history,
                                                            MyPageProceedResponseDto proceed,
                                                            MyPageScheduledResponseDto schedule,
                                                            MyPageEndResponseDto end){

        return MyPageResponseDto.builder().history(history)
                .proceed(proceed)
                .schedule(schedule)
                .end(end)
                .build();
    }
}
