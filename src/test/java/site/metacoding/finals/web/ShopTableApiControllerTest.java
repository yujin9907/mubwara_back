package site.metacoding.finals.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.imagefile.ImageFileRepository;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dto.reservation.ReservationReqDto.AnalysisDateReqDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopUpdateReqDto;
import site.metacoding.finals.dummy.DummyEntity;

@Sql({ "classpath:sql/dml.sql" })
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ShopTableApiControllerTest extends DummyEntity {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ImageFileRepository imageFileRepository;

    @BeforeEach
    public void setUp() {
        User ssar = newUser("ssar");
        User cos = newShopUser("cos");
    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void 주간통계요청테스트() throws Exception {
        //
        AnalysisDateReqDto dto = new AnalysisDateReqDto();
        dto.setDate("20221126");
        String body = om.writeValueAsString(dto);

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.post("/shop/table")
                        .content(body)
                        .contentType("application/json; charset=utf-8")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}