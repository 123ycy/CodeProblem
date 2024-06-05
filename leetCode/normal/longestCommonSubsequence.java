package normal;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: longestCommonSubsequence
 * @Description: 1143. 最长公共子序列
 * @Author: zhaooo
 * @Date: 2024/04/08 21:33
 */
public class longestCommonSubsequence {

    // 动态规划
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        // getAllLCS(text1, text2, text1.length(), text2.length(), dp, new StringBuilder());
        // set.forEach(System.out::println);
        return dp[text1.length()][text2.length()];
    }

    // 动态规划 并输出某个最长子序列
    public String longestCommonSubsequence2(String text1, String text2) {
        // dp[i][j] 表示text1,text2长度分别为i,j时lcs的长度
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        // 记录LCS长度为i时的对应char
        char[] ans = new char[Math.min(text1.length(), text2.length())];
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    // 当两个char相同时，不断更新对应长度的char
                    ans[dp[i][j]] = text1.charAt(i);
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return new String(ans).trim();
    }

    Set<String> set = new HashSet<>();

    // 输出所有LCS
    // https://blog.csdn.net/lisonglisonglisong/article/details/41596309
    void getAllLCS(String str1, String str2, int i, int j, int[][] dp, StringBuilder sb) {
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                // 逆向存放
                sb.append(str1.charAt(i - 1));
                // 向左上方走
                --i;
                --j;
            } else {
                // 两者都相同等于dp[i][j]
                if (dp[i - 1][j] == dp[j - 1][i]) {
                    // 此时向上向右均为LCS的元素
                    getAllLCS(str1, str2, i - 1, j, dp, new StringBuilder(sb));
                    getAllLCS(str1, str2, i, j - 1, dp, new StringBuilder(sb));
                    return;
                }
                // 向dp不变的方向走
                if (dp[i - 1][j] == dp[i][j]) {
                    --i;
                } else if (dp[i][j - 1] == dp[i][j]) {
                    --j;
                }
            }
        }
        set.add(sb.reverse().toString());
    }
}
