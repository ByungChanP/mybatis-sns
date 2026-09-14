package net.likelion.bebc25.sns.controller;


import jakarta.validation.Valid;
import net.likelion.bebc25.sns.dto.PostCreateRequest;
import net.likelion.bebc25.sns.dto.PostResponse;
import net.likelion.bebc25.sns.dto.PostSearchRequest;
import net.likelion.bebc25.sns.dto.PostUpdateRequest;
import net.likelion.bebc25.sns.security.principal.CustomUserDetails;
import net.likelion.bebc25.sns.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 조회, 검색
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPostList(
            @ModelAttribute PostSearchRequest searchRequest){
        // 검색어에 해당하는 게시글 목록 조회
        List<PostResponse> posts = postService.searchPosts(searchRequest);
        return ResponseEntity.ok(posts);
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
//            @RequestHeader("X-Member-Id") Long memberId, // 임시로 헤더에서 추출
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid PostCreateRequest request // JSON 요청 바디를
            // 객체로 자동 맵핑
    ){
        request.setMemberId(userDetails.getId());
        PostResponse createdPost = postService.createPost(request);
        URI location = URI.create("/api/v1/posts" + createdPost.id());
        return ResponseEntity.created(location).body(createdPost); //201
    }
    // 게시글 한건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long id) {
        // 전달받은 id를 이용하여 서비스 레이어의 게시글 한 건 조회 메서드를 호출
        PostResponse post = postService.getPostById(id);

        // 응답 받는 게시글 DTO를 ResponseEntity를 이용해 응답 상태 코드 200으로 응답
        return ResponseEntity.ok(post);
    }
    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("id") Long id,
//            @RequestHeader("X-Member-Id") Long memberId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody PostUpdateRequest request
            ){
//        // 수정 전에 게시글 정보 조회
//        PostResponse post = postService.getPostById(id);
//        // 본인의 게시글인지 확인
//        if(!post.memberId().equals(userDetails.getId())){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
        // 수정 작업
        postService.updatePost(id, request);
        // 수정된 게시글 조회
        PostResponse updatePost = postService.getPostById(id);
        // 응답 상태 코드 바디로 응답
        return ResponseEntity.ok(updatePost);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable("id") Long id,
//            @RequestHeader("X-Member-Id") Long memberId
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
//        // 수정 전에 게시글 정보 조회
//        PostResponse post = postService.getPostById(id);
//        // 본인의 게시글인지 확인
//        if(!post.memberId().equals(userDetails.getId())){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
        // 삭제 작업
        postService.deletePost(id);
        // 응답 상태 코드 바디로 응답
        return ResponseEntity.noContent().build();
    }
}
