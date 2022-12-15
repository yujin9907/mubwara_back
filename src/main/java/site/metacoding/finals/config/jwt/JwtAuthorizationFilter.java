package site.metacoding.finals.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("필터가 실행됨");

        if (JwtProcess.isHeaderVerify(request, response)) {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            // 유저 정보 넣어주기

            try {
                Long userId = JwtProcess.verify(token);

                User user = userRepository.findById(userId).get();
                PrincipalUser principalUser = new PrincipalUser(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser,
                        null, principalUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                System.out.println("익셉션 실행됨");
            }

        }

        System.out.println("필터가 종료됨");
        chain.doFilter(request, response);
    }

}
