package normal;

import java.util.*;

/**
 * @ClassName: LongestIncreasingSubsequence
 * @Description: 300. 最长递增子序列
 * @Author: zhaooo
 * @Date: 2024/02/13 13:58
 */
public class LongestIncreasingSubsequence {

    /**
     * 动态规划 + 排序
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        int ans = 0;
        // map[i][0] 表示以map[i][1]结尾的递增子序列长度
        int[][] map = new int[nums.length][2];
        for (int[] ints : map) {
            ints[1] = Integer.MIN_VALUE;
        }
        for (int num : nums) {
            for (int i = map.length - 1; i >= 0; i--) {
                if (map[i][1] < num) {
                    int len = map[i][0] + 1;
                    ans = Math.max(ans, len);
                    map[0][0] = len;
                    map[0][1] = num;
                    // 这里O(NlogN)导致时间复杂度比下面动态规划高
                    Arrays.sort(map, Comparator.comparingInt(x -> x[1]));
                    break;
                }
            }
        }
        return ans;
    }

    // 动态规划
    public int lengthOfLIS2(int[] nums) {
        int len = nums.length;
        // dp[i]表示以num[i]结尾的递增子序列长度
        int[] dp = new int[len];
        dp[0] = 1;
        int res = 1;
        for (int i = 1; i < len; i++) {
            int tmp = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    tmp = Math.max(tmp, dp[j] + 1);
                }
            }
            dp[i] = tmp;
            res = Math.max(res, tmp);
        }
        return res;
    }

    /**
     * 第一个 动态规划 的优化
     * 修改状态定义 + 利用子序列的递增性质进行二分查找
     * @param nums
     * @return
     */
    public int lengthOfLIS3(int[] nums) {
        int len = nums.length;
        // 其中每个元素 tails[k] 的值代表 长度为 k+1 的【最新】子序列尾部元素的值。
        // 【最新】保证了tails[k]递增 例如：[1, 100, 2, 3] 长度为2的子序列结尾会变成2而不是100
        int[] tails = new int[len];
        int res = 0;
        for (int num : nums) {
            // [< num][l, r][> num]
            int l = 0, r = res;
            // 二分查找num要插入的位置 下标+1即为len
            while (l < r) {
                int m = (l + r) / 2;
                if (tails[m] < num) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }
            // [< num][l=r][> num]
            // tails[l] 有可能大于num或者等于num
            // 保证最新 同时更新tails
            tails[l] = num;
            if (res == r) {
                res++;
            }
        }
        return res;
    }

    // 动态规划 并输出最长子序列
    public String lengthOfLIS4(int[] nums) {
        int len = nums.length;
        // dp[i]表示以num[i]结尾的递增子序列长度
        int[] dp = new int[len];
        int[] pre = new int[len];
        dp[0] = 1;
        int res = 1;
        int idx = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < len; i++) {
            int tmp = 1;
            for (int j = 0; j < i; j++) {
                // 当dp[j]+1 > tmp 时才记录前一个
                if (nums[i] > nums[j] && dp[j] + 1 > tmp) {
                    pre[i] = j;
                    tmp = dp[j] + 1;
                }
            }
            dp[i] = tmp;
            if (tmp == 1) {
                pre[i] = -1;
                continue;
            }
            if (tmp > res) {
                idx = i;
                res = tmp;
            }
        }
        while (idx != -1) {
            sb.append(nums[idx]);
            idx = pre[idx];
        }
        return sb.reverse().toString();
    }

    // 二分 并输出最长子序列
    public String lengthOfLIS5(int[] nums) {
        int len = nums.length;
        // tails修改为记录下标而不是值
        int[] tails = new int[len];
        // pre记录序列中下标的前一个元素，用于倒推
        int[] pre = new int[len];
        // 最长子序列的最后一个元素的下标
        int idx = 0;
        int res = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int num = nums[i];
            int l = 0, r = res;
            while (l < r) {
                int m = (l + r) / 2;
                if (nums[tails[m]] < num) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }
            // 插入下标
            tails[l] = i;
            // 记录当前插入下标的前一个下标
            // 若l等于0 即当元素没有前一个元素 pre[i] = -1
            pre[i] = l > 0 ? tails[l - 1] : -1;
            if (res == r) {
                res++;
                idx = i;
            }
        }
        while (idx != -1) {
            sb.append(nums[idx]);
            idx = pre[idx];
        }
        return sb.reverse().toString();
    }
}
