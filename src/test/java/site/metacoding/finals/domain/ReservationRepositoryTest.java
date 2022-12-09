package site.metacoding.finals.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.customer.CustomerRepository;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.imagefile.ImageFileRepository;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.reservation.ReservationQueryRepository;
import site.metacoding.finals.domain.reservation.ReservationRepository;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.shop_table.ShopTable;
import site.metacoding.finals.domain.shop_table.ShopTableRepository;
import site.metacoding.finals.domain.subscribe.Subscribe;
import site.metacoding.finals.domain.subscribe.SubscribeRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerJoinReqDto;
import site.metacoding.finals.dto.repository.shop.AnalysisDto;
import site.metacoding.finals.dto.test.testDto;
import site.metacoding.finals.dummy.DummyEntity;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ImageFileRepository imageFileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ShopTableRepository shopTableRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SubscribeRepository subscribeRepository;

    @BeforeEach
    public void setUp() {
        User user = newUser("ssar");
        userRepository.save(user);
        Customer customer = newCustomer(user);
        customerRepository.save(customer);

        Shop shop = newShop("가게1", "1", "한식");
        Shop shop2 = newShop("가게2", "2", "일식");
        shopRepository.save(shop);
        shopRepository.save(shop2);

        ShopTable shopTable = newShopTable(shop);
        shopTableRepository.save(shopTable);
        Reservation reservation = newReservation(customer, shopTable);
        reservationRepository.save(reservation);

        ImageFile imageFile = newShopImageFile(shop);
        imageFileRepository.save(imageFile);

        Subscribe subscribe = newSubscribe(customer, shop);
        subscribeRepository.save(subscribe);
    }

    @AfterEach
    public void tearDown() {
        em.createNativeQuery("ALTER TABLE customer ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE shop ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE users ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE shop_table ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE reservation ALTER COLUMN `id` RESTART WITH 1").executeUpdate();

    }

    @Test
    public void 예약커스터머아이디조회() {
        em.clear();

        Long CustomerId = 1L;

        List<Reservation> reservation = reservationRepository.findByCustomerId(CustomerId);

        log.debug("디버그 : " + reservation.get(0).getCustomer());

        // then
        assertEquals(reservation.get(0).getId(), 1);
    }

    @Test
    public void 인원수날짜에따른테이블목록테스트() {
        int maxPeople = 4;
        String date = "20221129";

        List<Reservation> reservation = reservationRepository.findByDataMaxPeople(maxPeople, date);

        log.debug("디버그 : " + reservation.size());

        // then
        assertEquals(reservation.get(0).getId(), 1);
    }

    @Test
    public void 예약커스터머목록보기테스트() {
        // g
        Long shopId = 1L;

        List<Reservation> reservations = reservationRepository.findCustomerByShopId(shopId);

        log.debug("디버그 : " + reservations.get(0).getReservationDate());

    }

    @Test
    public void 일일매출조회() {
        //
        Long shopId = 1L;
        String date = "20221129";

        //
        List<AnalysisDto> result = reservationRepository.findBySumDate(shopId, date);

        log.debug("디버그 : " + result.get(0).getResults());
        // dto(인터페이스) 문제 아님 확인
        // 예약어 아님 확인
        // 연산
    }

    @Test
    public void 주간매출조회() {
        //
        Long shopId = 1L;
        String date = "20221129";

        //
        List<AnalysisDto> result = reservationRepository.findBySumWeek(shopId);

        log.debug("디버그 : " + result.get(0).getResults());
        // dto(인터페이스) 문제 아님 확인
        // 예약어 아님 확인
        // 연산
    }

}
