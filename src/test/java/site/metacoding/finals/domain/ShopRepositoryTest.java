package site.metacoding.finals.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.menu.MenuRepository;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option.OptionRepository;
import site.metacoding.finals.domain.option_shop.OptionShop;
import site.metacoding.finals.domain.option_shop.OptionShopRepository;
import site.metacoding.finals.domain.reservation.Reservation;
import site.metacoding.finals.domain.reservation.ReservationRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopQueryRepository;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.shop_table.ShopTable;
import site.metacoding.finals.domain.shop_table.ShopTableRepository;
import site.metacoding.finals.domain.subscribe.Subscribe;
import site.metacoding.finals.domain.subscribe.SubscribeRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.repository.shop.ReservationRepositoryRespDto;
import site.metacoding.finals.dto.shop.ShopReqDto.OptionListReqDto;
import site.metacoding.finals.dummy.DummyEntity;

@Import(ShopQueryRepository.class)
@Slf4j
@DataJpaTest
@ActiveProfiles("test")
public class ShopRepositoryTest extends DummyEntity {

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
    private ShopQueryRepository shopQueryRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private OptionShopRepository optionShopRepository;
    @Autowired
    private MenuRepository menuRepository;

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

        Option option = newOption("옵션1");
        optionRepository.save(option);
        Option option2 = newOption("옵션2");
        optionRepository.save(option2);

        OptionShop optionShop = newOptionShop(shop, option);
        OptionShop optionShop2 = newOptionShop(shop, option2);
        OptionShop optionShop3 = newOptionShop(shop2, option);
        optionShopRepository.save(optionShop);
        optionShopRepository.save(optionShop2);
        optionShopRepository.save(optionShop3);

        Menu menu = newMenu(10000, shop);
        Menu menu2 = newMenu(20000, shop);
        Menu menu3 = newMenu(10000, shop2);
        menuRepository.save(menu);
        menuRepository.save(menu2);
        menuRepository.save(menu3);

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
        em.createNativeQuery("ALTER TABLE option ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_shop ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE menu ALTER COLUMN `id` RESTART WITH 1").executeUpdate();

    }

    @Test
    public void 유저네임테스트() {
        String username = "ssar";

        userRepository.findByUsername(username);

    }

    @Test
    public void 한건테스트() {
        em.clear();
        // sout으로 테스트하면 안 됨. to string하기 때문에 get 실행
        // shopRepository.findById(1L); // ManyToOne -> shop 셀렉트
        // OneToOne -> shop 셀렉트 + Image 셀렉트 (OneToOne은 Lazy가 작동하지 않는다.)

        List<Shop> shopsPS = shopRepository.findAllList();
        System.out.println("===============================");
        shopsPS.get(0).getImageFile().getId();
    }

    @Test
    public void findByResevationCustomerIdttTest() {
        Long customerId = 1L;

        List<ReservationRepositoryRespDto> dtos = shopRepository.findResevationByCustomerIdTEST(customerId);

        System.out.println("디버그 제피큐엘 :" + dtos.get(0).getAddress());
    }

    @Test
    public void findByResevationCustomerIdTest() {
        // Long customerId = 1L;

        // List<ReservationRepositoryRespDto> shop =
        // shopRepository.findResevationByCustomerId(customerId);

        // System.out.println("디버그 : " + shop.get(0).getStoreFilename());
    }

    @Test
    public void findSubscribeByCustomerIdTest() {
        log.debug("디버그 이미지 : " + imageFileRepository.findById(1L).get().getShop().getId());

        // given
        Long customerId = 1L;

        // when
        em.clear();
        List<Shop> shop = shopRepository.findSubscribeByCustomerId(customerId);

        // then
        log.debug("디버그 숍 안 이미지 : " + shop.get(0).getImageFile().getStoreFilename());
        assertEquals(customerId, shop.get(0).getId());

    }

    @Test
    public void findByCategoryTest() {
        em.clear();

        log.debug("디버그 이미지 : " + imageFileRepository.findById(1L).get().getShop().getId());

        String name = "한식";

        List<Shop> shop = shopRepository.findByCategory(name);

        assertEquals(shop.get(0).getCategory(), name);
    }

    @Test
    public void findByPopularListTest() {

        em.clear();

        shopRepository.findByPopularList();
    }

    @Test
    public void findOptionListByOptionIdTest() {
        OptionListReqDto dto = new OptionListReqDto();
        dto.setOption(1L);
        List<OptionListReqDto> test = new ArrayList<>();
        test.add(dto);

        em.clear();

        shopQueryRepository.findOptionListByOptionId(test);
    }

    @Test
    public void findByPriceListTest() {

        shopQueryRepository.findByPriceList("heiger");

    }

    @Test
    public void findBySearchListTest() {
        shopRepository.findBySearchList("가");
    }

    @Test
    public void findByLocationList() {
        shopRepository.findByLocationList("부산", "부산진구");
    }

}
