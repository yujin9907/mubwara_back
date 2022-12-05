package site.metacoding.finals.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDateUtil {
    public static String localDateTimeToStringFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy--MM-dd HH:mm:ss"));
    }
}
