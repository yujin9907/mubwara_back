package site.metacoding.finals.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.user.UserReqDto.LoginDto;
import site.metacoding.finals.dummy.DummyEntity;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class JWTAuthFilterTest extends DummyEntity {

        @Autowired
        private ObjectMapper om;
        @Autowired
        private MockMvc mvc;
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;
        @Autowired
        private UserRepository userRepository;

        @BeforeEach
        public void settup() throws Exception {
        }

        @Test
        public void 필터테스트() throws Exception {
                // given
                String password = bCryptPasswordEncoder.encode("123");

                User user = User.builder()
                                .username("fitertest")
                                .password(password)
                                .role(Role.USER)
                                .build();
                userRepository.save(user);

                LoginDto loginDto = new LoginDto();
                loginDto.setUsername("fitertest");
                loginDto.setPassword("123");

                String body = om.writeValueAsString(loginDto);

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.post("/login")
                                                .content(body)
                                                .contentType("application/json; charset=utf-8")
                                                .accept("application/json; charset=utf-8"));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void 메인테스트() throws Exception {
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.get("/")
                                                .accept("application/json; charset=utf-8"));

                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        }
}
