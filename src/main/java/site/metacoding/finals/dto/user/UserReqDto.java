package site.metacoding.finals.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.finals.domain.user.User;

public class UserReqDto {

    @Setter
    @Getter
    public static class JoinReqDto {
        private String username;
        private String password;
        private String Role;
    }

    @NoArgsConstructor // 테스트 용도
    @Getter
    @Setter
    public static class LoginDto {
        private String username;
        private String password;

        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .build();
        }
    }
}
