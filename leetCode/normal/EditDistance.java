package normal;

/**
 * @ClassName: EditDistance
 * @Description: 72. 编辑距离
 * @Author: zhaooo
 * @Date: 2024/02/20 13:08
 */
public class EditDistance {
    /**
     * dp
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        // 有一个字符串为空串
        if (n == 0 | m == 0) {
            return n + m;
        }
        // dp[i][j]表示 word1的前i个字符 -> word2的前j个字符 最小距离
        int[][] dp = new int[n + 1][m + 1];
        // 边界状态初始化
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j < m + 1; j++) {
            dp[0][j] = j;
        }
        // 计算所有 DP
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                // word1插入
                int left = dp[i - 1][j] + 1;
                // word2插入（等于word1删除）
                int down = dp[i][j - 1] + 1;
                // 改变一个字符
                int left_down = dp[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1);
                dp[i][j] = Math.min(left_down, Math.min(left, down));
            }
        }
        return dp[n][m];
    }

    /**
     * dfs + dp
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance2(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        return dfs(m - 1, n - 1, word1, word2, new int[m][n]);
    }

    // s1的下标为i以及之前的字符 -> s2的下标为j以及之前的字符 最小距离并保存在 cache[i][j]
    int dfs(int i, int j, String s1, String s2, int[][] cache) {
        if (i < 0 || j < 0) {
            // (j+1) + (i+1)
            return j + i + 2;
        }
        if (cache[i][j] != 0) {
            return cache[i][j];
        }
        if (s1.charAt(i) == s2.charAt(j)) {
            return cache[i][j] = dfs(i - 1, j - 1, s1, s2, cache);
        }
        // 同上
        return cache[i][j] = Math.min(dfs(i - 1, j - 1, s1, s2, cache),
                Math.min(dfs(i - 1, j, s1, s2, cache), dfs(i, j - 1, s1, s2, cache))) + 1;
    }
}
