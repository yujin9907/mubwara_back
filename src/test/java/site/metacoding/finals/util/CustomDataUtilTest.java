package site.metacoding.finals.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class CustomDataUtilTest {

    @Test
    public void 데이트포맷테스트() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();

        // WHEN
        String result = FormatDateUtil.localDateTimeToStringFormat(localDateTime);
        System.out.println("디버그 " + result);
    }
}
