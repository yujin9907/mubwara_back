package site.metacoding.finals.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dto.option.OptionReqDto.OptionSaveReqDto;
import site.metacoding.finals.dummy.DummyEntity;

@Sql("classpath:sql/dml.sql")
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class OptionShopApiControllerTest extends DummyEntity {

        @Autowired
        private ObjectMapper om;
        @Autowired
        private MockMvc mvc;

        // @BeforeEach
        // public void setUp() {
        // User cos = newUser("cos");
        // }

        @Test
        @WithUserDetails("cos")
        public void 옵션등록하기테스트() throws Exception {
                // g
                OptionSaveReqDto dto = new OptionSaveReqDto();
                List<OptionSaveReqDto> dtos = new ArrayList<>();
                dto.setOptionList(1L);
                dtos.add(dto);

                String body = om.writeValueAsString(dtos);

                // when
                ResultActions resultActions = mvc.perform(post("/auth/shop/option")
                                .content(body)
                                .contentType("application/json; charset=utf-8")
                                .accept("application/json; charset=utf-8"));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                log.debug(responseBody);

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

        }
}
