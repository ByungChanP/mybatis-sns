package net.likelion.bebc25.sns.dto;

// 로그인 성공시 토큰 객체 발행
public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn

) {
    public static TokenResponse of(String accessToken, String refreshToken, Long expiresIn){
        return new TokenResponse(accessToken, refreshToken, "Bearer", expiresIn);
    }
}
