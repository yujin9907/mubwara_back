package site.metacoding.finals.dto.user;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.user.User;

public class UserRespDto {

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
