package site.metacoding.finals.dto.user;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.domain.user.User;

public class UserReqDto {

    @Getter
    @Setter
    public static class KakaoDto {
        private Long id;
        private String connected_at;
        private KakaoAccount kakao_account;
        private PropertiesDto properties;

        @Getter
        public class PropertiesDto {
            private String nickname;
        }

        @Getter
        public class KakaoAccount {

            private String profile_nickname_needs_agreement;
            private String has_email;
            private String email_needs_agreement;
            private String is_email_valid;
            private String is_email_verified;
            private String email;
            private ProDto profile;

            @Getter
            public class ProDto {
                private String nickname;

            }
        }
    }

    @Setter
    @Getter
    public static class UpdateShopUser {
        private Long id;
        private String username;
        private String password;
        private Role role;
        private boolean isDeleted;

        public UpdateShopUser(PrincipalUser user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.role = Role.SHOP;
            this.isDeleted = false;
        }

        public User toEntity() {
            return User.builder()
                    .id(id)
                    .username(username)
                    .password(password)
                    .role(role)
                    .isDeleted(isDeleted)
                    .build();
        }

    }

    @Setter
    @Getter
    public static class JoinReqDto {
        private String username;
        private String password;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .role(Role.SHOP)
                    .isDeleted(Boolean.FALSE)
                    .build();
        }
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
