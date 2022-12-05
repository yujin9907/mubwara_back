package site.metacoding.finals.handler;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.user.UserReqDto.LoginDto;

@Slf4j
@Component
public class LoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        log.debug("디버그 : custom login handler 실행 success");

        ObjectMapper om = new ObjectMapper();

        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        ResponseDto<?> responseDto = new ResponseDto<>(HttpStatus.OK, "로그인 성공", null); // 리스폰스 디티오 만들기
        String responseBody = om.writeValueAsString(responseDto);

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(200);
        response.getWriter().println(responseBody);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        log.debug("디버그 : custom login handler 실행 failure");

        ObjectMapper om = new ObjectMapper();
        ResponseDto<?> responseDto = new ResponseDto<>(HttpStatus.BAD_REQUEST, "로그인 실패", null);
        String responseBody = om.writeValueAsString(responseDto);

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(400);
        response.getWriter().println(responseBody);

    }

}
