package normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LengthOfLongestFibonacciSubsequence
 * @Description: 873. 最长的斐波那契子序列的长度
 * @Author: zhaooo
 * @Date: 2022/7/9/11:11
 */
public class LengthOfLongestFibonacciSubsequence {
    /**
     * 动态规划
     * @param arr
     * @return
     */
    public int lenLongestFibSubseq(int[] arr) {
        int res = 0;
        int len = arr.length;
        int max = arr[len - 1];
        Map<Integer, Integer> map = new HashMap<>();
        // dp[i][j]表示以arr[i]开始下一个是arr[j]的最长长度
        // 不用一维数组为了保持i和下一个之间的关系
        int[][] dp = new int[len][len];
        for (int i = len - 1; i >= 0; i--) {
            map.put(arr[i], i);
            for (int j = i + 1; j < len; j++) {
                int next = arr[i] + arr[j];
                if (next <= max && map.getOrDefault(next, 0) != 0) {
                    dp[i][j] = Math.max(dp[j][map.getOrDefault(next, 0)] + 1, 3);
                    res = Math.max(dp[i][j], res);
                }
            }
        }
        return res;
    }
}
