package net.likelion.bebc25.sns.domain;

import lombok.*;

import javax.xml.stream.Location;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Member {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private String profileImage;
    @Builder.Default
    private String role = "ROLE_USER";
    private LocalDateTime createdAt;
}
