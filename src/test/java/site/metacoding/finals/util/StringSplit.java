package site.metacoding.finals.util;

import org.junit.jupiter.api.Test;

public class StringSplit {
    @Test
    public void 문자열나누기테스트() {
        String str = "a.b.c.d";
        int idx = str.lastIndexOf(".");
        System.out.println("디버그 " + idx);
        System.out.println("디버그 : " + str.substring(idx));
    }
}
