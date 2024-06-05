package normal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: MaximumSumCircularSubarray
 * @Description: 918. 环形子数组的最大和
 * https://leetcode.cn/problems/maximum-sum-circular-subarray/solutions/2350660/huan-xing-zi-shu-zu-de-zui-da-he-by-leet-elou/
 * @Author: zhaooo
 * @Date: 2023/8/17/9:18
 */
public class MaximumSumCircularSubarray {
    /**
     * 分情况讨论，是否经过nums[len-1]
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular(int[] nums) {
        int len = nums.length;
        // max[i]表示[i,len]之间，以len结尾的最大值
        // 长度为len+1 保证max[i + 1]不越界
        int[] max = new int[len + 1];
        int ans = nums[0], sum = 0, cur = 0;
        for (int i = len - 1; i >= 0; i--) {
            sum += nums[i];
            max[i] = Math.max(max[i + 1], sum);
        }
        sum = 0;
        // 参考 MaximumSubarray：53. 最大子数组和
        for (int i = 0; i < len; i++) {
            sum += nums[i];
            // cur表示以[0,i]之间以nums[i]结尾的最大值
            cur = Math.max(cur + nums[i], nums[i]);
            // sum + max[i + 1]表示向前经过nums[len]的最大值
            ans = Math.max(ans, Math.max(cur, sum + max[i + 1]));
        }
        return ans;
    }

    /**
     * 环形数组的最大子数组和为 nums[0:i]和nums[j:n]时，找到普通数组最小的子数组 nums[i:j]即可
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular2(int[] nums) {
        int n = nums.length;
        int preMax = nums[0], maxRes = nums[0];
        int preMin = nums[0], minRes = nums[0];
        int sum = nums[0];
        for (int i = 1; i < n; i++) {
            preMax = Math.max(preMax + nums[i], nums[i]);
            maxRes = Math.max(maxRes, preMax);
            preMin = Math.min(preMin + nums[i], nums[i]);
            minRes = Math.min(minRes, preMin);
            sum += nums[i];
        }
        // 如果 maxRes<0，数组中不包含大于等于0的元素
        if (maxRes < 0) {
            return maxRes;
        } else {
            return Math.max(maxRes, sum - minRes);
        }
    }

    /**
     * 前缀和+滑动窗口+单调队列
     * 将数组延长一倍，即对于 i >= n 的元素，令 nums[i]=nums[i−n],转换为了在一个长度为 2n 的数组上，寻找长度不超过 n 的最大子数组和。
     * s[i]表示[0,i]的前缀和，找到最大的s[i]-s[j], 考虑所有满足 i−n <= j < i，用单调队列维护该集合
     * <p>
     * 滑动窗口保证长度不超过 n （没有重复使用），
     * 前缀和取差可以快速计算子数组和，
     * 单调队列保留可能的最优解，可以这么理解：单调队列里保存的都是之前的前缀和，作为被减项（两个前缀和的差值就是中间子数组的和）计算中间子数组的和，所以被减项越小越好。
     * 这有两方面的好处 1. 队列头的被减项最小，对当前前缀和来说，减去头部的被减项的差值最大，用于更新结果；
     * 2. 在把当前前缀和入栈之前，要处理队尾的元素，即 如果队尾前缀和大于当前前缀和，没有必要继续保留队尾的前缀和，
     * 同样的原因：因为当前的前缀和作为被减项更小，计算出来的差值更大，而且，队尾的前缀和更早被滑动窗口 n 长度的限制剪掉，而当前的前缀和被剪掉的更晚
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular3(int[] nums) {
        int n = nums.length;
        Deque<int[]> queue = new ArrayDeque<int[]>();
        int pre = nums[0], res = nums[0];
        queue.offerLast(new int[]{0, pre});
        for (int i = 1; i < 2 * n; i++) {
            while (!queue.isEmpty() && queue.peekFirst()[0] < i - n) {
                queue.pollFirst();
            }
            pre += nums[i % n];
            res = Math.max(res, pre - queue.peekFirst()[1]);
            // 单调增队列
            while (!queue.isEmpty() && queue.peekLast()[1] >= pre) {
                queue.pollLast();
            }
            queue.offerLast(new int[]{i, pre});
        }
        return res;
    }
}
