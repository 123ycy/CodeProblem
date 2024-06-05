package normal;

/**
 * @ClassName: InterleavingString
 * @Description: 97. 交错字符串
 * @Author: zhaooo
 * @Date: 2023/09/04 16:27
 */
public class InterleavingString {
    char[] cs1, cs2, cs3;

    /**
     * dfs 超时
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        cs1 = s1.toCharArray();
        cs2 = s2.toCharArray();
        cs3 = s3.toCharArray();
        return dfs(0, 0, 0);
    }

    boolean dfs(int i, int j, int k) {
        if (k == cs3.length) {
            return true;
        }
        boolean ans = false;
        if (i < cs1.length && cs1[i] == cs3[k]) {
            ans = dfs(i + 1, j, k + 1);
        }
        if (j < cs2.length && cs2[j] == cs3[k]) {
            ans |= dfs(i, j + 1, k + 1);
        }
        return ans;
    }

    /**
     * 动态规划
     * https://leetcode.cn/problems/interleaving-string/solutions/335561/lei-si-lu-jing-wen-ti-zhao-zhun-zhuang-tai-fang-ch/?envType=study-plan-v2&envId=top-interview-150
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave2(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if (s3.length() != m + n) {
            return false;
        }
        // 动态规划，dp[i,j]表示s1前i字符能与s2前j字符组成s3前i+j个字符；
        boolean[][] dp = new boolean[m + 1][n + 1];
        // 初始化
        dp[0][0] = true;
        for (int i = 1; i <= m && s1.charAt(i - 1) == s3.charAt(i - 1); i++) {
            dp[i][0] = true;
        }
        for (int j = 1; j <= n && s2.charAt(j - 1) == s3.charAt(j - 1); j++) {
            dp[0][j] = true;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // s3.charAt(i + j - 1)表示只向某个方向移动一格
                dp[i][j] = (dp[i - 1][j] && s3.charAt(i + j - 1) == s1.charAt(i - 1)) || (dp[i][j - 1] && s3.charAt(i + j - 1) == s2.charAt(j - 1));
            }
        }
        return dp[m][n];
    }
}