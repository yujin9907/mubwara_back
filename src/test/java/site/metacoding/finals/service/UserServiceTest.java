package site.metacoding.finals.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dummy.DummyEntity;

// junit 에서 mockito 사용하기 위한 설정
@MockitoSettings(strictness = Strictness.WARN) // mock 객체 생성의 강한 제약을 해제하는 설정
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends DummyEntity {

    //

    // 단위 테스트는 통합테스트보다 가볍게 진행할 수 있도록 springboottest 보다 mockito를 통해서 가볍게 테스트

    // mock 객체를 반환(가짜 객체 생성)
    @Mock
    private UserRepository userRepository;

    // mock 과 spy 로 생성된 가짜 객체 주입
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {

    }

    // mocking 방법
    // https://jaehoney.tistory.com/219

    @DisplayName("mockito 서비스 레이어 테스트")
    @Test
    public void 유저중복체크테스트() {
        // g
        // User user = new User(null, null, null, null, null, null, null);

        // doReturn(newUser("test")).when(userRepository).save(any(User.class));

        doReturn(newUser("test")).when(userRepository).findByUsername("test");

        User userPS = userRepository.findByUsername("test");
        System.out.println(userPS);

        // when
        String checkUser = userService.checkUsername("test");

        // then
        assertEquals("중복", checkUser);
        // assertEquals(, null);
    }

    @Test
    void 조인서비스테스트() {

    }

}
