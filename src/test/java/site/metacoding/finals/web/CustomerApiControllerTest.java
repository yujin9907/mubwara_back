package site.metacoding.finals.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerJoinReqDto;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerUpdateReqDto;
import site.metacoding.finals.dummy.DummyEntity;

@Sql("classpath:sql/dml.sql")
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class CustomerApiControllerTest extends DummyEntity {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private EntityManager em;

    // @Autowired
    // private ShopRepository shopRepository;
    // @Autowired
    // private ImageFileRepository imageFileRepository;
    // @Autowired
    // private UserRepository userRepository;
    // @Autowired
    // private CustomerRepository customerRepository;
    // @Autowired
    // private ShopTableRepository shopTableRepository;
    // @Autowired
    // private ReservationRepository reservationRepository;
    // @Autowired
    // private SubscribeRepository subscribeRepository;

    // @BeforeEach
    // public void setUp() {
    // User user = newUser("ssar");
    // userRepository.save(user);
    // Customer customer = newCustomer(user);
    // customerRepository.save(customer);

    // Shop shop = newShop("가게1", "1", "한식");
    // Shop shop2 = newShop("가게2", "2", "일식");
    // shopRepository.save(shop);
    // shopRepository.save(shop2);

    // ShopTable shopTable = newShopTable(shop);
    // shopTableRepository.save(shopTable);
    // Reservation reservation = newReservation(customer, shopTable);
    // reservationRepository.save(reservation);

    // ImageFile imageFile = newShopImageFile(shop);
    // imageFileRepository.save(imageFile);

    // Subscribe subscribe = newSubscribe(customer, shop2);
    // subscribeRepository.save(subscribe);
    // em.persist(imageFile);
    // em.persist(shop);
    // em.flush();
    // em.clear();
    // }

    @Test
    public void 커스터머회원가입() throws Exception {
        // g
        CustomerJoinReqDto customerJoinReqDto = new CustomerJoinReqDto();
        customerJoinReqDto.setName("테스트");
        customerJoinReqDto.setAddress("주소테스트");
        customerJoinReqDto.setPhoneNumber("테스트입니다");
        customerJoinReqDto.setUsername("테스트아이디");
        customerJoinReqDto.setPassword("테스트비번");

        String ctdata = om.writeValueAsString(customerJoinReqDto);

        // when
        ResultActions resultActions = mvc.perform(post("/customer/join")
                .content(ctdata)
                .contentType("application/json; charset=utf-8")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void 회원정보업데이트() throws Exception {
        Long id = 1L;
        CustomerUpdateReqDto dto = new CustomerUpdateReqDto();
        dto.setName("업데이트됨");
        dto.setAddress("업데이트주소");
        dto.setPhoneNumber("01011112222");
        String data = om.writeValueAsString(dto);

        //
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/customer/" + id)
                .content(data)
                .contentType("application/json; charset=utf-8")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug("디버그 :" + responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void 마이페이지예약목록() throws Exception {
        // 지연로딩할 때 터짐 근데 조인 컬럼 필요 없어서 jsonignore 달아서 처리함
        //
        Long id = 1L;

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/customer/mypage/reservation/" + id)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 마이페이지구독목록() throws Exception {
        //
        // em.flush();
        // em.clear();
        Long id = 1L;

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/customer/mypage/subscribe/" + id)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 마이페이지리뷰목록() throws Exception {
        //
        Long id = 1L;

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/customer/mypage/review/" + id)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
