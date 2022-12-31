package site.metacoding.finals.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class CodingTest {
    static int a;

    @Test
    public void testa() {
        a++;
        System.out.println(a);

        // System.out.println(10 / 0);
    }

    @Test
    public void solution111(Integer[] arr) {
        List<Integer> sourceList = Arrays.asList(0, 1, 2, 3, 4, 5);
        Set<Integer> targetSet = new HashSet<>(Arrays.asList(arr));
    }

    @Test
    public void solution11() {
        boolean answer = true;

        int[] t = { 1, 2, 3 };
        int[] t2 = { 1, 2, 3 };

        if (t == t2) {
            System.out.println(" 같음");
        }
        System.out.println("다름");
        System.out.println(t == t2);

        // return answer;
    }

    @Test
    public boolean solution1(int[] arr) {
        boolean answer = true;
        int num = 0;

        for (int i = 1; i <= arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] == i) {
                    num++;
                }
            }
        }

        System.out.println(arr.length);
        System.out.println(num);

        if (num != arr.length) {
            answer = false;
        }

        return answer;
    }

    @Test
    public int solution(int[][] office, int r, int c, String[] move) {
        int answer = 0;
        int locationValue = office[r][c];

        for (int i = 0; i < move.length; i++) {
            if (move[i].equals("go")) {

            }
        }

        return answer;
    }
}
