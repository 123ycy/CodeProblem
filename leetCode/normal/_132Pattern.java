package normal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @ClassName: _132Pattern
 * @Description: 456. 132 模式
 * @Author: zhaooo
 * @Date: 2023/04/11 13:45
 */
public class _132Pattern {
    /**
     * 暴力 超时
     * 访问到i时记录i之前的最大值最小值，只要nums[i]在最大值最小值之间即可返回
     * @param nums
     * @return
     */
    public boolean find132pattern(int[] nums) {
        int len = nums.length;
        if (len < 3) {
            return false;
        }
        // 记录一对low和high
        List<int[]> lowAndHigh = new ArrayList<>();
        // 记录当前最小值
        int low = nums[0];
        for (int i = 1; i < len; i++) {
            // 比low都小，更新low，准备重新开辟一对low和high
            if (nums[i] <= low) {
                low = nums[i];
                continue;
            }
            for (int[] ints : lowAndHigh) {
                // 更新high
                if (nums[i] >= ints[1]) {
                    ints[1] = nums[i];
                // 即ints[0] < nums[i] && nums[i] < ints[1]
                } else if (ints[0] < nums[i]) {
                    return true;
                }
            }
            // 比low大
            lowAndHigh.add(new int[]{low, nums[i]});
        }
        return false;
    }

    /**
     * 单调栈
     * https://leetcode.cn/problems/132-pattern/solutions/676970/xiang-xin-ke-xue-xi-lie-xiang-jie-wei-he-95gt/?orderBy=most_votes
     * @param nums
     * @return
     */
    public boolean find132pattern2(int[] nums) {
        int n = nums.length;
        // 单调减栈
        Deque<Integer> d = new ArrayDeque<>();
        // 顺序是 i j k 大小是 nums[i]<nums[j]>num[k] num[i]<num[k]
        // k 记录所有出栈元素的最大值（k 代表满足 132 结构中的 2）
        // 使用单调栈找最接近元素的能力
        int k = Integer.MIN_VALUE;
        // 这样做的本质是：我们通过维护「单调递减」来确保已经找到了有效的 (j,k)。
        // 换句话说如果 k 有值的话，那么必然是因为有 j > k，导致的有值 (因为出栈得到k)。
        // 也就是 132 结构中，我们找到了 32，剩下的 i （也就是 132 结构中的 1）则是通过遍历过程中与 k 的比较来找到。
        // 这样做的复杂度是 O(n) 的，比树状数组还要快
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] < k) return true;
            // 此时nums[i]当作nums[j]
            // 弹出比nums[i]小的
            // 不能用栈底元素当nums[j]因为栈底可能是nums[len-1]
            while (!d.isEmpty() && d.peekLast() < nums[i]) {
                k = d.pollLast();
            }
            // nums[j]加入已访问元素，可能后面有比nums[j]更大的
            d.addLast(nums[i]);
        }
        return false;
    }
}
