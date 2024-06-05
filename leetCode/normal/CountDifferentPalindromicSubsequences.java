package normal;

import java.util.Arrays;

/**
 * @ClassName: CountDifferentPalindromicSubsequences
 * @Description: 730. 统计不同回文子序列
 * @Author: zhaooo
 * @Date: 2022/6/10/19:22
 */
public class CountDifferentPalindromicSubsequences {
    /**
     * 区间dp
     * https://leetcode.cn/problems/count-different-palindromic-subsequences/solution/tong-ji-butong-by-jiang-hui-4-q5xf/
     * @param s
     * @return
     */
    public int countPalindromicSubsequences(String s) {
        int mod = (int) (1e9 + 7);
        int n = s.length();
        int[][] dp = new int[n][n];
        //一个单字符是一个回文子序列
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        //从长度为2的子串开始计算
        for (int len = 2; len <= n; len++) {
            //挨个计算长度为len的子串的回文子序列个数
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;
                //情况(1) 相等
                if (s.charAt(i) == s.charAt(j)) {
                    int left = i + 1;
                    int right = j - 1;
                    //找到第一个和s[i]相同的字符
                    while (left <= right && s.charAt(left) != s.charAt(i)) {
                        left++;
                    }
                    //找到第一个和s[j]相同的字符
                    while (left <= right && s.charAt(right) != s.charAt(j)) {
                        right--;
                    }
                    if (left > right) {
                        //情况① 没有重复字符
                        dp[i][j] = 2 * dp[i + 1][j - 1] + 2;
                    } else if (left == right) {
                        //情况② 出现一个重复字符
                        dp[i][j] = 2 * dp[i + 1][j - 1] + 1;
                    } else {
                        //情况③ 有两个及两个以上
                        dp[i][j] = 2 * dp[i + 1][j - 1] - dp[left + 1][right - 1];
                    }
                } else {
                    //情况(2) 不相等
                    dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1];
                }
                //处理超范围结果
                dp[i][j] = (dp[i][j] >= 0) ? dp[i][j] % mod : dp[i][j] + mod;
            }
        }
        return dp[0][n - 1];
    }

    /**
     * 区间dp
     * https://leetcode.cn/problems/count-different-palindromic-subsequences/solution/by-ac_oier-lbva/
     * @param s
     * @return
     */
    public int countPalindromicSubsequences2(String s) {
        int MOD = (int) (1e9 + 7);
        char[] cs = s.toCharArray();
        int n = cs.length;
        int[][] f = new int[n][n];
        int[] L = new int[4], R = new int[4];
        Arrays.fill(L, -1);
        for (int i = n - 1; i >= 0; i--) {
            L[cs[i] - 'a'] = i;
            Arrays.fill(R, -1);
            for (int j = i; j < n; j++) {
                R[cs[j] - 'a'] = j;
                for (int k = 0; k < 4; k++) {
                    if (L[k] == -1 || R[k] == -1) continue;
                    int l = L[k], r = R[k];
                    if (l == r) f[i][j] = (f[i][j] + 1) % MOD;
                    else if (l == r - 1) f[i][j] = (f[i][j] + 2) % MOD;
                    else f[i][j] = (f[i][j] + f[l + 1][r - 1] + 2) % MOD;
                }
            }
        }
        return f[0][n - 1];
    }
}
