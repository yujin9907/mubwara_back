package site.metacoding.finals.config.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;

public class JwtProcess {

    public static String create(PrincipalUser principalUser, int time) {
        String jwtToken = JWT.create()
                .withSubject("auth")
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withClaim("id", principalUser.getUser().getId())
                .withClaim("username", principalUser.getUsername())
                .withClaim("role", principalUser.getUser().getRole().name())
                .sign(Algorithm.HMAC256(JwtSecret.SECRET));

        return jwtToken;
    }

    public static String createRefresh(int time) {
        String jwtToken = JWT.create()
                .withSubject("refress-auth")
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .sign(Algorithm.HMAC256(JwtSecret.SECRET));

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

    public static Long verify(String token) throws TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JwtSecret.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        return id;

    }
}
