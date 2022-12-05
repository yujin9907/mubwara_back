package site.metacoding.finals.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.test.jsonObjectMapping;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @PostMapping("/json/test")
    public jsonObjectMapping jsonphasingtest(@RequestBody jsonObjectMapping jObjectMapping) {
        return jObjectMapping;
    }

    @GetMapping("/")
    public ResponseDto<?> main() {
        return new ResponseDto<>(HttpStatus.OK, "OK", null);
    }

    @GetMapping("/auth/user/test")
    public void test() {
        System.out.println("실행됨");
    }

}
