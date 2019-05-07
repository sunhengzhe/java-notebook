package algorithm.dp.LCS;

public class RecursiveLCS {
    public int getLength(String s, String t) {
        if (s.length() == 0 || t.length() == 0) {
            return 0;
        }

        if (s.charAt(0) == t.charAt(0)) {
            return 1 + getLength(s.substring(1), t.substring(1));
        }

        return Math.max(getLength(s, t.substring(1)), getLength(s.substring(1), t));
    }
}
