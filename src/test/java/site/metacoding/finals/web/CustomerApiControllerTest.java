package site.metacoding.finals.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerJoinReqDto;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerSaveReqDto;
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

    @BeforeEach
    public void setUp() {
        User ssar = newUser("ssar");
    }

    @WithUserDetails("ssar")
    @Test
    public void ?????????????????????????????????() throws Exception {
        // g
        CustomerSaveReqDto dto = new CustomerSaveReqDto();
        dto.setAddress("???????????????");
        dto.setName("???????????????");
        dto.setPhoneNumber("01055556666");

        String body = om.writeValueAsString(dto);

        // when
        ResultActions resultActions = mvc.perform(post("/user/save/customer")
                .content(body)
                .contentType("application/json; charset=utf-8")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void ????????????????????????() throws Exception {
        // g
        CustomerJoinReqDto customerJoinReqDto = new CustomerJoinReqDto();
        customerJoinReqDto.setName("?????????");
        customerJoinReqDto.setAddress("???????????????");
        customerJoinReqDto.setPhoneNumber("??????????????????");
        customerJoinReqDto.setUsername("??????????????????");
        customerJoinReqDto.setPassword("???????????????");

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

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void ????????????????????????() throws Exception {
        Long id = 1L;
        CustomerUpdateReqDto dto = new CustomerUpdateReqDto();
        dto.setName("???????????????");
        dto.setAddress("??????????????????");
        dto.setPhoneNumber("01011112222");
        String data = om.writeValueAsString(dto);

        //
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/user/customer")
                .content(data)
                .contentType("application/json; charset=utf-8")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug("????????? :" + responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @WithUserDetails("ssar")
    @Test
    public void ????????????() throws Exception {

        //
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.delete("/user/customer")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug("????????? :" + responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void ???????????????????????????() throws Exception {
        // ??????????????? ??? ?????? ?????? ?????? ?????? ?????? ????????? jsonignore ????????? ?????????
        //
        Long id = 1L;

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/auth/mypage/reservation")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("ssar")
    @Test
    public void ???????????????????????????() throws Exception {
        //
        // em.flush();
        // em.clear();
        Long id = 1L;

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/auth/mypage/subscribe")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("ssar")
    @Test
    public void ???????????????????????????() throws Exception {
        //
        Long id = 1L;

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/auth/mypage/review")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
