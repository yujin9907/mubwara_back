package site.metacoding.finals.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dummy.DummyEntity;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class TestControllerTest extends DummyEntity {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        User ssar = newUser("ssar");
        User cos = newShopUser("cos");
    }

    @Test
    public void 오브젝트메퍼파싱테스트() throws Exception {
        // g
        String jsonData = "{\"id\":1,\"name\":\"첫번째\",\"innerClass\":{\"id\":2,\"name\":\"두번째\"}}";

        // when
        ResultActions resultActions = mvc.perform(post("/json/test")
                .content(jsonData)
                .contentType("application/json; charset=utf-8")
                .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
