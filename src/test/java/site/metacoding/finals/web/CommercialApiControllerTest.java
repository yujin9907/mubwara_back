package site.metacoding.finals.web;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
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

import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.dummy.DummyEntity;

@Sql("classpath:sql/dml.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class CommercialApiControllerTest extends DummyEntity {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        User ssar = newUser("ssar");
    }

    @Test
    public void 광고리스트보기() throws Exception {
        //

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/commercial")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
