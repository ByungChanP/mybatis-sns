package net.likelion.bebc25.sns.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PostDetailResponse {
    @Schema(description = "게시글 등록 식별자", example = "1")
    private Long id;

    @Schema(description = "게시글 본문 상세 내용", example = "스프링 부터 학습중", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "게시글 이미지 URL", example = "example.png", nullable = true)
    private String imageUrl;

    @Schema(description = "게시글 등록 날짜", example = "01월01일 01시 11분 11초")
    private LocalDateTime createdAt;

    @Schema(description = "게시글 작성자", example = "박씨", requiredMode = Schema.RequiredMode.REQUIRED)
    private MemberResponse author;

    // 댓글이 없는 경우 NullPointerException 방지 및 빌더 패턴 사용 시 빈 리스트 유지를 위한 기본값 설정
    @Builder.Default
    private List<CommentResponse> comments = new ArrayList<>();
}