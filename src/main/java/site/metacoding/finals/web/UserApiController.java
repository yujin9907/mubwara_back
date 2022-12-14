package site.metacoding.finals.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.config.jwt.JwtProcess;
import site.metacoding.finals.config.jwt.JwtSecret;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.user.UserReqDto.JoinReqDto;
import site.metacoding.finals.dto.user.UserRespDto.JoinRespDto;
import site.metacoding.finals.dto.user.UserRespDto.OauthLoginRespDto;
import site.metacoding.finals.handler.OauthHandler;
import site.metacoding.finals.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final OauthHandler oauthHandler;
    private final UserService userService;

    @GetMapping("/join/{username}")
    public ResponseEntity<?> checkSameUsername(@PathVariable String username) {
        String check = userService.checkUsername(username);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "아이디 중복 체크 여부", check),
                HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinShopApi(@RequestBody JoinReqDto joinReqDto) {
        JoinRespDto respDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "가게(유저) 회원가입 완료", respDto),
                HttpStatus.CREATED);
    }

    @GetMapping(value = "/oauth/{serviceName}", headers = "access-token")
    public void oauthKakao(@RequestHeader("access-token") String token, @PathVariable String serviceName,
            HttpServletResponse response) throws IOException {

        System.out.println("디버그 토큰 : " + token);

        OauthLoginRespDto respDto = oauthHandler.processKakaoLogin(serviceName, token);

        try {
            ObjectMapper om = new ObjectMapper();
            String responseBody = om.writeValueAsString(respDto);

            response.setContentType("application/json; charset=utf-8");
            response.setHeader("access-token", respDto.getToken());
            response.setHeader("refresh-token", respDto.getRefreshToken());
            response.setStatus(201);
            response.getWriter().println(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeApiException("파싱 에러", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/refresh/token", headers = "refresh-token")
    public void refreshToken(@RequestHeader("refresh-token") String token, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String refresh = request.getHeader("refresh-token").replace("Bearer ", "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JwtSecret.SECRET)).build().verify(refresh);

        Long id = decodedJWT.getClaim("id").asLong();
        User userPS = userService.findById(id);
        PrincipalUser principalUser = new PrincipalUser(userPS);
        String access = JwtProcess.create(principalUser, (1000 * 60 * 60));

        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(HttpStatus.CREATED, "엑세스 토큰발급완료", null);
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setHeader("access-token", access);
            response.setStatus(201);
            response.getWriter().println(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeApiException("파싱 에러", HttpStatus.EXPECTATION_FAILED);
        }

    }
}
