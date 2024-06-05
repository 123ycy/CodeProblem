package normal;

/**
 * @ClassName: IsSubsequence
 * @Description: 392. 判断子序列
 * @Author: zhaooo
 * @Date: 2022/8/30/18:23
 */
public class IsSubsequence {
    /**
     * 贪心
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        int n = s.length(), m = t.length();
        int i = 0, j = 0;
        while (i < n && j < m) {
            // 如果匹配到相同的继续向前走不用回溯
            // 例如 abc ababc
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == n;
    }

    /**
     * 动态规划
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence2(String s, String t) {
        int n = s.length(), m = t.length();
        // dp数组dp[i][j]表示字符串t以i位置开始第一次出现字符j的位置
        int[][] dp = new int[m + 1][26];
        // 初始化边界条件，dp[i][j] = m表示t中不存在字符j
        for (int i = 0; i < 26; i++) {
            dp[m][i] = m;
        }
        // 从后往前递推初始化dp数组
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (t.charAt(i) == j + 'a') {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }
        int add = 0;
        for (int i = 0; i < n; i++) {
            if (dp[add][s.charAt(i) - 'a'] == m) {
                // t中没有s[i] 返回false
                return false;
            }
            // 否则直接跳到t中s[i]第一次出现的位置之后一位
            add = dp[add][s.charAt(i) - 'a'] + 1;
        }
        return true;
    }
}
