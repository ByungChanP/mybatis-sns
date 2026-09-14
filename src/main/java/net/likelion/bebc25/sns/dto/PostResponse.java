package net.likelion.bebc25.sns.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

// 게시글 한건 응답, 한번 생성되면 값을 변경할 수 없음
public record PostResponse(
        @Schema(description = "게시글 등록 식별자", example = "1")
        Long id,
        @Schema(description = "게시글 등록한 회원 id", example = "10")
        Long memberId,

        @Schema(description = "게시글 한 건 본문내용", example = "스프링 부터 학습중", requiredMode = Schema.RequiredMode.REQUIRED)
        String content,
        @Schema(description = "게시글 이미지 URL", example = "example.png", nullable = true)
        String imageUrl,
        @Schema(description = "게시글 좋아요 갯수", example = "1")
        int likeCount,

        @Schema(description = "게시글 등록 날짜", example = "01월01일 01시 11분 11초")
        LocalDateTime createdAt,

        @Schema(description = "게시글 갱신 날짜", example = "01월01일 01시 11분 11초")
        LocalDateTime updatedAt

) {
    // 신규 게시글 등록 요청 DTO로부터 게시글 응답 DTO를 생성하는 팩토리 메서드
    public static PostResponse from(PostCreateRequest dto){
        return new PostResponse(
                dto.getId(),
                dto.getMemberId(),
                dto.getContent(),
                dto.getImageUrl(),
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
