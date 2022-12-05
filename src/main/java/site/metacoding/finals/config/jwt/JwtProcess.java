package site.metacoding.finals.config.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.log.LogDelegateFactory;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.domain.user.User;

@Slf4j
public class JwtProcess {

    public static String create(PrincipalUser principalUser) {
        String jwtToken = JWT.create()
                .withSubject("auth")
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60)))
                .withClaim("id", principalUser.getUser().getId())
                .withClaim("username", principalUser.getUsername())
                .withClaim("role", principalUser.getUser().getRole().name())
                .sign(Algorithm.HMAC256(JwtSecret.SECRET));

        System.out.println("디버그" + principalUser.getUser().getRole().name());
        return jwtToken;
    }

    public static boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        } else {
            return true;
        }
    }

    public static UserDetails verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JwtSecret.SECRET)).build().verify(token);

        Long id = decodedJWT.getClaim("id").asLong();
        String username = decodedJWT.getClaim("username").asString();
        System.out.println("디버그 : " + username);
        String role = decodedJWT.getClaim("role").asString();
        System.out.println("디버그 : " + role);
        User user = User.builder().id(id).username(username).role(Role.valueOf(role)).build();
        PrincipalUser principalUser = new PrincipalUser(user);
        return principalUser;
    }

}
