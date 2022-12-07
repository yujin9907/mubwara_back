package site.metacoding.finals.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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

    @GetMapping(value = "/oauth/{serviceName}", headers = "accessToken")
    public ResponseEntity<?> oauthKakao(@RequestHeader("accessToken") String token, @PathVariable String serviceName,
            HttpServletResponse response) {

        System.out.println("디버그 토큰 : " + token);

        OauthLoginRespDto respDto = oauthHandler.processKakaoLogin(serviceName, token);

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "카카오 로그인", respDto),
                HttpStatus.OK);

    }
}
