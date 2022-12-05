package site.metacoding.finals.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.user.UserReqDto.LoginDto;
import site.metacoding.finals.handler.LoginHandler;

@Slf4j
public class JwtAutenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAutenticationFilter(AuthenticationManager authenticationManager, LoginHandler loginHandler) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        super.setAuthenticationFailureHandler(loginHandler);
        super.setAuthenticationSuccessHandler(loginHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            ObjectMapper om = new ObjectMapper();
            LoginDto loginDto = om.readValue(request.getInputStream(), LoginDto.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        PrincipalUser principalUser = (PrincipalUser) authResult.getPrincipal();

        String jwtToken = JwtProcess.create(principalUser);

        response.addHeader("Authorization", "Bearer " + jwtToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        log.debug("디버그 : unsuccessfulAuthentication 요청됨");
        this.getFailureHandler().onAuthenticationFailure(request, response, failed);
    }

}
