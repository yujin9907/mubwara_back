package site.metacoding.finals.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.domain.user.User;

public class UserRespDto {

    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;
        private Role role;
        private LocalDateTime createdAt;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.createdAt = user.getCreatedAt();
        }

    }

    @Setter
    @Getter
    public static class OauthLoginRespDto {
        private String returnPath;
        private String token;
        private User user;

        public OauthLoginRespDto(String returnPath, String token, User user) {
            this.returnPath = returnPath;
            this.token = token;
            this.user = user;
        }

    }

}
