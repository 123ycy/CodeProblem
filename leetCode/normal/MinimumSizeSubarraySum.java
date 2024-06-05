package normal;

import java.util.Arrays;

/**
 * @ClassName: MinimumSizeSubarraySum
 * @Description: 209. 长度最小的子数组
 * @Author: zhaooo
 * @Date: 2023/07/09 14:54
 */
public class MinimumSizeSubarraySum {
    /**
     * 滑动窗口
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int len = nums.length;
        int min = Integer.MAX_VALUE;
        // 子数组 [l,r) 的长度为 r-l
        // l < len为了当 r==len 时遍历完所有长度
        for (int l = 0, r = 0, sum = 0; l < len; ) {
            while (sum < target) {
                if (r < len) {
                    sum += nums[r++];
                } else {
                    break;
                }
            }
            if (sum >= target) min = Math.min(min, r - l);
            sum -= nums[l++];
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * 前缀和 + 二分查找
     * @param s
     * @param nums
     * @return
     */
    public int minSubArrayLen2(int s, int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        int[] sums = new int[n + 1];
        // 为了方便计算，令 size = n + 1
        // sums[0] = 0 意味着前 0 个元素的前缀和为 0
        // sums[1] = A[0] 前 1 个元素的前缀和为 A[0]
        // 以此类推
        for (int i = 1; i <= n; i++) {
            sums[i] = sums[i - 1] + nums[i - 1];
        }
        for (int i = 1; i <= n; i++) {
            int target = s + sums[i - 1];
            // 使用二进制搜索算法搜索指定值的指定字节数组。在进行此调用之前，数组必须按照sort(byte[])方法进行排序。
            // 如果没有排序，结果是未定义的。如果数组包含具有指定值的多个元素，则不能保证将找到哪个元素。
            int bound = Arrays.binarySearch(sums, target);
            // 如果numbers中没有key，那么binarySearch方法会返回一个负数，表示key应该插入的位置。这个负数的计算方法是：- (插入点) - 1。
            if (bound < 0) {
                // 即比target即s + sums[i - 1]大的第一个位置
                bound = -bound - 1;
            }
            if (bound <= n) {
                ans = Math.min(ans, bound - (i - 1));
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}
