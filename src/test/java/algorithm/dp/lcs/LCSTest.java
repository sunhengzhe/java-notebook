package algorithm.dp.lcs;

import algorithm.dp.LCS.IterativeLCS;
import algorithm.dp.LCS.MemoizingLCS;
import algorithm.dp.LCS.RecursiveLCS;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class LCSTest {
    @Test
    public void should_get_correct_length() {
        assertEquals(3, new RecursiveLCS().getLength("aaaaa", "aaa"));
        assertEquals(7, new RecursiveLCS().getLength("nematode knowledge", "empty bottle"));

        assertEquals(3, new MemoizingLCS().getLength("aaaaa", "aaa"));
        assertEquals(7, new MemoizingLCS().getLength("nematode knowledge", "empty bottle"));

        assertEquals(3, new IterativeLCS().getLength("aaaaa", "aaa"));
        assertEquals(7, new IterativeLCS().getLength("nematode knowledge", "empty bottle"));
    }

    @Test
    public void test_long_string() {
        String s = "";
        for (int i = 0; i < 8; i++) {
            if (i == 5) {
                s += "nematode knowledge";
            } else {
                s += "z";
            }
        }

        String t = "empty bottle";

        System.out.println("--- recursive ---");
        long startTime = new Date().getTime();
        assertEquals(7, new RecursiveLCS().getLength(s, t));
        System.out.println(new Date().getTime() - startTime);

        System.out.println("--- memoization ---");
        startTime = new Date().getTime();
        assertEquals(7, new MemoizingLCS().getLength(s, t));
        System.out.println(new Date().getTime() - startTime);

        System.out.println("--- iterative ---");
        startTime = new Date().getTime();
        assertEquals(7, new IterativeLCS().getLength(s, t));
        System.out.println(new Date().getTime() - startTime);
    }
}
