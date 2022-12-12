package site.metacoding.finals.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.exception.JwtExceptionHandler;
import site.metacoding.finals.config.exception.RuntimeApiException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("필터가 실행됨");

        if (JwtProcess.isHeaderVerify(request, response)) {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            // try {
            PrincipalUser principalUser = (PrincipalUser) JwtProcess.verify(token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser,
                    null, principalUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("정상적으로됨 " + authentication);
            // } catch (Exception e) {
            // System.out.println("익셉션 실행됨");
            // JwtExceptionHandler.sendError(HttpStatus.FORBIDDEN, "엑세스 토큰 만료됨 재요청 필요",
            // response);
            // }

        }

        System.out.println("필터가 종료됨");
        chain.doFilter(request, response);
    }

}
