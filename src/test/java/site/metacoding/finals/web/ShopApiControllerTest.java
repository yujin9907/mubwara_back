package site.metacoding.finals.web;

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
import site.metacoding.finals.domain.image_file.ImageFileRepository;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.dummy.DummyEntity;

@Sql({ "classpath:sql/dml.sql" })
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ShopApiControllerTest extends DummyEntity {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ImageFileRepository imageFileRepository;

    // @BeforeEach
    // public void setUp() {

    // Shop shop = newShop("가게1", "1", "한식");
    // Shop shop2 = newShop("가게2", "2", "일식");

    // shopRepository.save(shop);
    // shopRepository.save(shop2);

    // ImageFile imageFile = newShopImageFile(shop);
    // imageFileRepository.save(imageFile);
    // }

    @Test
    public void 가게전체목록테스트() throws Exception {
        //

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/shop/list")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 카테고리별목록보기테스트() throws Exception {
        //
        String name = "한식";

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/shop/list/" + name)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}