package site.metacoding.finals.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

public class KakaoUserTest {

    @Getter
    @Setter
    public class testOauthKakao {
        private String[] property_keys;
        private Boolean secure_resource;
    }

    public static void main(String[] args) throws Exception {
        // 카카오유저불러오기("oAoFSYnI8kcYAo_5rvlTHJE8FJYLGXsgmi7zqA4PCj1z6wAAAYTlZ76s");
        String token = "ogIDDrItKI45Om_lJ8JQEvJ-IAeZMsMI4IzlHvkiCj1y6wAAAYTl3YPx";

        인가코드확인(token);
        System.out.println("---------------------------------");
        create2kakao(token);
    }

    public static void 인가코드확인(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String reqURL = "https://kauth.kakao.com/v1/user/access_token_info";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ");

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                reqURL,
                HttpMethod.GET,
                request,
                String.class);

        System.out.println(response.getStatusCode());

    }

    public static void 카카오유저불러오기(String token) {
        // GET/POST /v2/user/me HTTP/1.1
        // Host: kapi.kakao.com
        // Authorization: Bearer ${ACCESS_TOKEN}/KakaoAK ${APP_ADMIN_KEY}
        // Content-type: application/x-www-form-urlencoded;charset=utf-8
        RestTemplate restTemplate = new RestTemplate();
        String reqURL = "https://kauth.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                reqURL,
                HttpMethod.GET,
                request,
                String.class);

        // (2)

        // // (3)
        // String reqURL = "https://kauth.kakao.com/v2/user/me";
        // String adminAppKey = "c0d33506ee27a80657d7542347ed1605";

        // HttpHeaders headers = new HttpHeaders();
        // headers.add("Content-type",
        // "application/x-www-form-urlencoded;charset=utf-8");
        // headers.add("Authorization", "Bearer " + token);
        // // headers.add("Authorization", "KakaoAK " + adminAppKey);

        // // // (4)
        // // MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // // params.add("grant_type", "authorization_code");
        // // params.add("client_id", "a471b131916596e9f882cce06006e7a0");
        // // params.add("redirect_uri", "https://kauth.kakao.com/oauth/token");
        // // params.add("code", token);

        // // (5)
        // HttpEntity kakaoTokenRequest = new HttpEntity(headers);

        // // (6)
        // ResponseEntity<String> accessTokenResponse = rt.exchange(
        // "https://kapi.kakao.com/v2/user/me",
        // HttpMethod.GET,
        // kakaoTokenRequest,
        // String.class);

        // (7)
        ObjectMapper objectMapper = new ObjectMapper();
        testOauthKakao oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), testOauthKakao.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(oauthToken);

    }

    public static void createKakaoUser(String token) throws Exception {

        // GET/POST /v2/user/me HTTP/1.1
        // Host: kapi.kakao.com

        String reqURL = "https://kauth.kakao.com//v2/user/me";
        String adminAppKey = "c0d33506ee27a80657d7542347ed1605";

        // access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); // 전송할
                                                                         // header
                                                                         // 작성,
                                                                         // access_token전송
            // conn.setRequestProperty("Authorization", "KakaoAK " + adminAppKey); // 전송할

            HashMap<String, Object> userInfo = new HashMap<>();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // JsonParser parser = new JsonParser();
            // JsonElement element = parser.parse(result);

            // JsonObject properties =
            // element.getAsJsonObject().get("properties").getAsJsonObject();
            // JsonObject kakao_account =
            // element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            // String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            // String email = kakao_account.getAsJsonObject().get("email").getAsString();

            // userInfo.put("nickname", nickname);
            // userInfo.put("email", email);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void create2kakao(String token) {

        RestTemplate restTemplate = new RestTemplate();

        // 헤더 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + token);

        // 요청 url
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = null;

        // 요청
        try {

            response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class);

            System.out.println("응답결과 :" + response.getBody());

        } catch (HttpStatusCodeException e) {

            System.out.println("error :" + e);

        }

        System.out.println(response.getBody());
    }

    public static void 인가코드받기() { // 프론트에서 엑세스 토큰 대신 테스트
        // GET
        // /oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code
        // HTTP/1.1
        // Host: kauth.kakao.com

        String reqURL = "https://kauth.kakao.com/oauth/authorize?client_id=a471b131916596e9f882cce06006e7a0&redirect_uri=https://kauth.kakao.com/oauth/token&response_type=code";

        // access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
