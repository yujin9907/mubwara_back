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
import site.metacoding.finals.dto.menu.MenuReqDto.MenuSaveReqDto;
import site.metacoding.finals.dummy.DummyEntity;

@Sql("classpath:sql/dml.sql")
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class MenuApiControllerTest extends DummyEntity {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        User ssar = newUser("ssar");
        User cos = newShopUser("cos");
    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void 메뉴목록보기테스트() throws Exception {
        // g : 토큰 가게 회원

        // w
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/shop/menu")
                .accept("application/json; charset=utf-8"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        // t
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void 메뉴생성하기테스트() throws Exception {
        // g : 토큰 가게 회원
        List<String> images = new ArrayList<>();
        images.add("dsklfjds");

        MenuSaveReqDto dto = new MenuSaveReqDto();
        dto.setName("테스트");
        dto.setPrice(10);
        dto.setRecommanded(1);
        dto.setImageFile(images);

        String body = om.writeValueAsString(dto);

        // w
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/shop/menu")
                .contentType("application/json; charset=utf-8")
                .content(body)
                .accept("application/json; charset=utf-8"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        // t
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void 메뉴삭제하기테스트() throws Exception {
        // g : 토큰 가게 회원

        // w
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.delete("/shop/menu/" + 1L)
                .accept("application/json; charset=utf-8"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        // t
        resultActions.andExpect(MockMvcResultMatchers.status().isAccepted());
    }

}
