package net.likelion.bebc25.sns.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostLikeMapperTest {
    @Autowired
    private PostLikeMapper postLikeMapper;

    @Test
    @DisplayName("좋아요 등록 및 등록 여부 조회")
    void insertAndCountLikeTest(){
        // given 1번 회원인 2번 게시글에 좋아요를 등록
        Long memberId =1L;
        Long postId = 2L;

        // when 좋아요 이전/이후의 카운트를 조회
        int beforeCount = postLikeMapper.countLike(memberId, postId);
        postLikeMapper.insertLike(memberId,postId);
        int afterCount = postLikeMapper.countLike(memberId, postId);

        // then 등록 이전에는 count 0 이후에는 1을 기대
        assertThat(beforeCount).isEqualTo(0);
        assertThat(afterCount).isEqualTo(1);

    }
    @Test
    @DisplayName("좋아요 취소 테스트")
    void deleteLikeTest(){
        // given 1번 회원인 2번 게시글에 좋아요를 등록
        Long memberId =1L;
        Long postId = 2L;
        postLikeMapper.insertLike(memberId,postId);

        // when 좋아요를 취소
        postLikeMapper.deleteLike(memberId,postId);

        // then 좋아요 감소를 확인
        int count = postLikeMapper.countLike(memberId, postId);
        assertThat(count).isEqualTo(0);
    }
}
