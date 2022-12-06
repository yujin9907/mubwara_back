package site.metacoding.finals.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.handler.OauthHandler;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final OauthHandler oauthHandler;

    @GetMapping(value = "/oauth/{serviceName}", headers = "accessToken")
    public ResponseEntity<?> oauthKakao(@RequestHeader("accessToken") String data, @PathVariable String serviceName,
            HttpServletResponse response) {

        System.out.println("디버그 토큰 : " + data);

        String userToken = oauthHandler.processKakaoLogin(serviceName, data);
        response.setHeader("Authorization", userToken);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "카카오 로그인", null),
                HttpStatus.CREATED);

    }
}
