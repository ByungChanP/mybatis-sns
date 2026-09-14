package net.likelion.bebc25.sns.service;

import net.likelion.bebc25.sns.dto.LikeToggleResponse;
import net.likelion.bebc25.sns.dto.PostResponse;
import net.likelion.bebc25.sns.mapper.PostLikeMapper;
import net.likelion.bebc25.sns.mapper.PostMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@Transactional
public class PostLikeServiceTest {

    @Autowired
    private PostLikeService postLikeService;

    @Autowired
    private PostLikeMapper postLikeMapper;

    @Autowired
    private PostMapper postMapper;

    @Test
    @DisplayName("좋아요 토글 등록, 카운트 증가 테스트")
    void toggleLikeAddTest() {
        // given: 1번 회원이 2번 게시글에 좋아요 시도
        Long memberId = 1L;
        Long postId = 2L;

        PostResponse beforePost = postMapper.findById(postId);
        int beforeLikeCount = beforePost.likeCount();
        boolean beforeLiked = postLikeMapper.countLike(memberId, postId) > 0;

        // when: 좋아요 토글
        LikeToggleResponse result = postLikeService.toggleLike(memberId, postId);

        // then
        PostResponse afterPost = postMapper.findById(postId);
        boolean afterLiked = postLikeMapper.countLike(memberId, postId) > 0;
        if (beforeLiked) { // 토글 이전에 좋아요 상태일 경우라면 토글 이후에는 좋아요 상태가 아니고 좋아요 수는 -1이 되어야 함
            assertThat(result.liked()).isFalse();
            assertThat(result.likeCount()).isEqualTo(beforeLikeCount - 1);
            assertThat(afterLiked).isFalse();
            assertThat(afterPost.likeCount()).isEqualTo(beforeLikeCount - 1);
        } else { // 토글 이전에 좋아요 상태가 아닌 경우라면 토글 이후에는 좋아요 상태가 되고 좋아요 수는 +1이 되어야 함
            assertThat(result.liked()).isTrue();
            assertThat(result.likeCount()).isEqualTo(beforeLikeCount + 1);
            assertThat(afterLiked).isTrue();
            assertThat(afterPost.likeCount()).isEqualTo(beforeLikeCount + 1);
        }
    }
//    @Test
//    @DisplayName("좋아요 토글, 카운트 수 변경 중 예외 발생시 롤백 테스트")
//    void toggleLikeRollbackTest(){
//        // given: 1번 회원이 2번 게시글에 좋아요 시도
//        Long memberId = 1L;
//        Long postId = 2L;
//        postLikeMapper.deleteLike(memberId, postId);
//
////        postMapper.increaseLikeCount(postId); // 정상 실행
//
//        doThrow(new RuntimeException("데이터베이스 네트워크 장애 발생"))
//                .when(postLikeMapper).increaseLikeCount(postId);
//
//        // when: 좋아요 토글
//        LikeToggleResponseDto result = postLikeService.toggleLike(memberId, postId);
//        assertThat(result.liked()).isTrue();
//
//    }
}
