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

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.customer.CustomerRepository;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.imagefile.ImageFileRepository;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.reservation.ReservationQueryRepository;
import site.metacoding.finals.domain.reservation.ReservationRepository;
import site.metacoding.finals.domain.review.Review;
import site.metacoding.finals.domain.review.ReviewRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.shop_table.ShopTable;
import site.metacoding.finals.domain.shop_table.ShopTableRepository;
import site.metacoding.finals.domain.subscribe.Subscribe;
import site.metacoding.finals.domain.subscribe.SubscribeRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.repository.customer.ScoreRespDto;
import site.metacoding.finals.dto.repository.shop.ReservationRepositoryRespDto;
import site.metacoding.finals.dto.test.testDto;
import site.metacoding.finals.dummy.DummyEntity;

@Import(ReservationQueryRepository.class)
@Slf4j
@DataJpaTest
@ActiveProfiles("test")
public class ReviewRepositoryTest extends DummyEntity {

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
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReservationQueryRepository reservationQueryRepository;

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

        Review review = newReview(customer, shop, 5);
        Review review2 = newReview(customer, shop, 4);
        reviewRepository.save(review);
        reviewRepository.save(review2);

    }

    @AfterEach
    public void tearDown() {
        em.createNativeQuery("ALTER TABLE customer ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE shop ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE users ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE shop_table ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE reservation ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE image_file ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE subscribe ALTER COLUMN `id` RESTART WITH 1").executeUpdate();

    }

    @Test
    public void qlrm테스트() {
        List<testDto> t = reservationQueryRepository.findByTest();

        log.debug("디버그 : " + t.get(0).getId());
    }

    @Test
    public void 가게평점보기테스트() {
        //
        Long shopId = 1L;

        //
        ScoreRespDto review = reviewRepository.findByAvgScore(shopId);

        System.out.println(review.getScore());

    }

}
