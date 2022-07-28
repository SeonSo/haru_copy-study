package com.project1.haruco.web.dto.response.posting;

import com.project1.haruco.web.domain.posting.Posting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
public class PostingResponseDto {

    private Long postingId;
    private String postingImg;
    private String postingContent;
    private Long postingCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public PostingResponseDto(Posting posting) {
        this.postingContent = posting.getPostingContent();
        this.postingImg = posting.getPostingImg();
        this.postingCount=posting.getPostingCount();
        this.createdAt = posting.getCreatedAt();
        this.modifiedAt =posting.getModifiedAt();
    }
}
