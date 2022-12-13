package site.metacoding.finals.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StringSplit {
    @Test
    public void 문자열나누기테스트() {
        String str = "a.b.c.d";
        int idx = str.lastIndexOf(".");
        System.out.println("디버그 " + idx);
        System.out.println("디버그 : " + str.substring(idx));
    }

    @Test
    public void tostring테스트() {
        List<Long> test = new ArrayList<>();
        test.add(1L);

        System.out.println("디버그" + test.get(0).toString());
    }
}
