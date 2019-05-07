package algorithm.dp.LCS;

/**
 * Memoization（Top down）
 */
public class MemoizingLCS {
    private String s;
    private String t;

    /**
     * s * t table
     */
    private int[][] cache;

    public int getLength(String _s, String _t) {
        s = _s;
        t = _t;

        cache = new int[s.length() + 1][t.length() + 1];

        for (int i = 0; i < s.length() + 1; i++) {
            for (int j = 0; j < t.length() + 1; j++) {
                cache[i][j] = -1;
            }
        }

        return subProblem(0, 0);
    }

    public int subProblem(int i, int j) {
        if (cache[i][j] > -1) {
            return cache[i][j];
        }

        if (i == s.length() || j == t.length()) {
            return cache[i][j] = 0;
        }

        if (s.charAt(i) == t.charAt(j)) {
            return cache[i][j] = 1 + subProblem(i + 1, j + 1);
        }

        return cache[i][j] = Math.max(subProblem(i + 1, j), subProblem(i, j + 1));
    }
}
