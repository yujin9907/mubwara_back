package site.metacoding.finals.handler;

import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.config.jwt.JwtProcess;
import site.metacoding.finals.config.jwt.JwtSecret;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.user.UserReqDto.KakaoDto;
import site.metacoding.finals.dto.user.UserRespDto.OauthLoginRespDto;

@RequiredArgsConstructor
@Component
public class OauthHandler {

    private final UserRepository userRepository;

    public OauthLoginRespDto processKakaoLogin(String serviceName, String token) {
        String result = "";

        // 회원정보 받아오기
        String kakaoResult = getKakaoUser(token);
        // 파싱
        KakaoDto kakaoUser = josnToDto(kakaoResult);
        // 존재 확인 : db에서 조회 후 (없으면) 인서트
        String username = serviceName + kakaoUser.getId();
        User userPS = userRepository.findByUsername(username);

        if (userPS == null) {
            userPS = createDBData(username);
            result = "회원 가입 진행 필요";
        } else {
            result = "카카오 유저 로그인 성공";
        }
        
        // 토큰 발급
        String resultToken = createToken(userPS);
        String resultRefreshToken = JwtProcess.createRefresh(1000 * 60 * 60 * 24);

        return new OauthLoginRespDto(result, resultToken, resultRefreshToken);
    }

    public String getKakaoUser(String token) {

        // 헤더 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + token);

        // 요청 url
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        // 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class);

            System.out.println("응답결과 :" + response.getBody());

        } catch (RuntimeException e) {
            new RuntimeApiException("통신에러", HttpStatus.BAD_GATEWAY);
        }

        return response.getBody();

    }

    public KakaoDto josnToDto(String json) {
        ObjectMapper om = new ObjectMapper();
        KakaoDto kakaoUser = new KakaoDto();
        try {
            kakaoUser = om.readValue(json, KakaoDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("디버그 파싱 : " + kakaoUser.getId());
        return kakaoUser;
    }

    public User createDBData(String username) {
        User saveUser = User.builder()
                .username(username)
                .password(username + UUID.randomUUID().toString())
                .role(Role.USER)
                .isDeleted(Boolean.FALSE)
                .build();
        userRepository.save(saveUser);
        return saveUser;
    }

    public String createToken(User user) {
        String jwtToken = JWT.create()
                .withSubject("oauth")
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60)))
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().name())
                .sign(Algorithm.HMAC256(JwtSecret.SECRET));

        return jwtToken;
    }

}
