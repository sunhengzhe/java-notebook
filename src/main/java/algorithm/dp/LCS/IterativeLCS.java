package algorithm.dp.LCS;

/**
 * Iterative（Bottom up）
 */
public class IterativeLCS {

    public int getLength(String s, String t) {
        int[][] cache = new int[s.length() + 1][t.length() + 1];

        for (int i = s.length(); i >= 0; i--) {
            for (int j = t.length(); j >= 0; j--) {
                if (i == s.length() || j == t.length()) {
                    cache[i][j] = 0;
                } else if (s.charAt(i) == t.charAt(j)) {
                    cache[i][j] = 1 + cache[i + 1][j + 1];
                } else {
                    cache[i][j] = Math.max(cache[i][j + 1], cache[i + 1][j]);
                }
            }
        }

        return cache[0][0];
    }
}
