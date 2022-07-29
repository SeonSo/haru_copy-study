package com.project1.haruco.web.dto.request.posting;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class PostingUpdateRequestDto {
    private String postingImg;

    @NotBlank(message = "빈 내용")
    private String postingContent;

    @Builder
    public PostingUpdateRequestDto(String postingImg, String postingContent){
        this.postingImg = postingImg;
        this.postingContent = postingContent;
    }
}
