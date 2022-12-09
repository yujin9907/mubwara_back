package site.metacoding.finals.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import site.metacoding.finals.config.auth.PrincipalUser;

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
            PrincipalUser principalUser = (PrincipalUser) JwtProcess.verify(token);

            // System.out.println("필터 : " + principalUser.getUser().getRole());
            // System.out.println("유저 왜 안 됨 : " +
            // principalUser.getUser().getRole().getValue());
            // System.out.println("유저 왜 안 됨 : " + principalUser.getUser().getRole().name());

            Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser,
                    null, principalUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("정상적으로됨 " + authentication);
        }

        System.out.println("필터가 종료됨");
        chain.doFilter(request, response);
    }

}
