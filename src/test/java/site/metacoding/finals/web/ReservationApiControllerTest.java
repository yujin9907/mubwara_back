package site.metacoding.finals.web;

import java.util.List;

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
import site.metacoding.finals.domain.reservation.ReservationRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dto.reservation.ReservationReqDto.ReservationSaveReqDto;
import site.metacoding.finals.dto.reservation.ReservationReqDto.ReservationSelectReqDto;
import site.metacoding.finals.dummy.DummyEntity;

@Sql("classpath:sql/dml.sql")
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ReservationApiControllerTest extends DummyEntity {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ShopRepository shopRepository;

    @BeforeEach
    public void setUp() {
        User ssar = newUser("ssar");
        User cos = newShopUser("cos");
    }

    @Test
    public void 테이블목록조회테스트() throws Exception {
        // g
        ReservationSelectReqDto reqDto = new ReservationSelectReqDto();
        reqDto.setShopId(1L);
        reqDto.setDate("20221127");
        reqDto.setMaxPeople(4);
        String body = om.writeValueAsString(reqDto);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/reservation/person")
                .content(body)
                .contentType("application/json; charset=utf-8")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void 타임리스트조회테스트() throws Exception {
        // g

        ReservationSelectReqDto reqDto = new ReservationSelectReqDto();
        reqDto.setShopId(1L);
        reqDto.setDate("20221129");
        reqDto.setMaxPeople(4);
        String body = om.writeValueAsString(reqDto);

        System.out.println("숍 아이디 디버그 : " + shopRepository.findById(1L));
        List<Shop> shop = shopRepository.findAll();
        shop.forEach((s) -> System.out.println("디버그 : " + s));

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/reservation/time")
                .content(body)
                .contentType("application/json; charset=utf-8")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void 예약저장하기테스트() throws Exception {
        // g
        ReservationSaveReqDto dto = new ReservationSaveReqDto();
        dto.setMaxPeople(4);
        dto.setReservationDate("20221126");
        dto.setReservationTime("16");
        dto.setShopId(1L);
        String body = om.writeValueAsString(dto);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/auth/reservation")
                .content(body)
                .contentType("application/json; charset=utf-8")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void 예약커스터머전체목록보기() throws Exception {
        //

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/shop/reservation")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
