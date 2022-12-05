package site.metacoding.finals.service;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import site.metacoding.finals.domain.reservation.ReservationRepository;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

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
    public void 타임리스트생성테스트() {

    }

}
