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
import site.metacoding.finals.dto.shop.ShopReqDto.OptionListReqDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopSaveReqDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopUpdateReqDto;
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

    @BeforeEach
    public void setUp() {
        User ssar = newUser("ssar");
        User cos = newShopUser("cos");
    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void 가게등록하기테스트() throws Exception {
        //
        List<String> images = new ArrayList<>();
        images.add("dskjlsdf");

        ShopSaveReqDto dto = new ShopSaveReqDto();
        dto.setShopName("테스트");
        dto.setPhoneNumber("1");
        dto.setAddress("주소테스트");
        dto.setCategory("한식");
        dto.setInformation("설명");
        dto.setOpenTime("1");
        dto.setCloseTime("1");
        dto.setPerHour(1);
        dto.setPerPrice(1);
        dto.setImage(images);

        String body = om.writeValueAsString(dto);

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.post("/user/shop/save")
                        .content(body)
                        .contentType("application/json; charset=utf-8")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
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
                MockMvcRequestBuilders.post("/shop/analysis/week")
                        .content(body)
                        .contentType("application/json; charset=utf-8")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void 일일통계요청테스트() throws Exception {
        //
        AnalysisDateReqDto dto = new AnalysisDateReqDto();
        dto.setDate("20221126");
        String body = om.writeValueAsString(dto);

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.post("/shop/analysis/date")
                        .content(body)
                        .contentType("application/json; charset=utf-8")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 가게전체목록테스트() throws Exception {
        //

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/list")
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
                MockMvcRequestBuilders.get("/list/" + name)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 가게상세보기테스트() throws Exception {
        //
        Long shopId = 1L;

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/detail/" + shopId)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void 가게업데이트페이지테스트() throws Exception {
        //

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/shop/update")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void 가게업데이트테스트() throws Exception {
        //
        List<String> images = new ArrayList<>();
        images.add("ZGZhZGZhZg==");

        ShopUpdateReqDto reqDto = new ShopUpdateReqDto();
        reqDto.setShopName("테스트");
        reqDto.setPhoneNumber("0390324234");
        reqDto.setAddress("주소주소");
        reqDto.setCategory("양식");
        reqDto.setInformation("변경된 소개");
        reqDto.setOpenTime("12");
        reqDto.setCloseTime("22");
        reqDto.setPerPrice(10);
        reqDto.setPerHour(1);
        reqDto.setImage(images);

        String body = om.writeValueAsString(reqDto);

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.put("/shop/update")
                        .contentType("application/json; charset=utf-8")
                        .content(body)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 인기순목록보기테스트() throws Exception {
        //

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/list/popular")
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 편의시설순목록보기테스트() throws Exception {
        //
        OptionListReqDto dto = new OptionListReqDto();
        dto.setOption(1L);
        OptionListReqDto dto2 = new OptionListReqDto();
        dto2.setOption(2L);
        List<OptionListReqDto> dtos = new ArrayList<>();
        dtos.add(dto);
        dtos.add(dto2);

        System.out.println("인풋데이터 : " + dtos.get(1).getOption());

        String body = om.writeValueAsString(dtos);

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.post("/list/option")
                        .contentType("application/json; charset=utf-8")
                        .content(body)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 가격순목록보기테스트() throws Exception {
        //
        String str = "higher";

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/list/price/" + str)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 검색목록보기테스트() throws Exception {
        //
        String str = "가";

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/list/search/" + str)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 지역별목록보기테스트() throws Exception {
        //
        String city = "부산";
        String region = "부산진구";

        //
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/list/location?city=" + city + "&region=" + region)
                        .accept("application/json; charset=utf-8"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(responseBody);

        //
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}