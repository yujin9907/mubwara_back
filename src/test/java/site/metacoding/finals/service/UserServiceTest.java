package site.metacoding.finals.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import site.metacoding.finals.domain.reservation.ReservationRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // https://galid1.tistory.com/772

    // 단위 테스트는 통합테스트보다 가볍게 진행할 수 있도록 springboottest 보다 mockito를 통해서 가볍게 테스트

    // 1. 빈 리포지토리 만들기
    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void 유저중복체크테스트() {

    }

}
