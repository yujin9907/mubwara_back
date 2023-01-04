package site.metacoding.finals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

// @Profile("dev")
@ActiveProfiles("dev")
@SpringBootTest
class FinalApplicationTests {

	@Test
	void contextLoads() {
	}

}
