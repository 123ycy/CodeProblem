package normal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * @ClassName: SlidingWindowMaximum
 * @Description: 239. 滑动窗口最大值
 * @Author: zhaooo
 * @Date: 2024/02/27 11:50
 */
public class SlidingWindowMaximum {
    // 堆/优先队列 超时
    public int[] maxSlidingWindow(int[] nums, int k) {
        int idx = 0;
        int[] ret = new int[nums.length - k + 1];
        PriorityQueue<Integer> pq = new PriorityQueue<>(k, (a, b) -> (b - a));
        for (; idx < k; idx++) {
            pq.add(nums[idx]);
        }
        ret[0] = pq.peek();
        for (; idx < nums.length; idx++) {
            pq.remove(nums[idx - k]);
            pq.add(nums[idx]);
            ret[idx - k + 1] = pq.peek();
        }
        return ret;
    }

    // 堆/优先队列 不超时
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int n = nums.length;
        // 这里我们传入了一个比较器，当两者的值相同时，比较下标的位置，下标大的在前面。
        PriorityQueue<int[]> queue = new PriorityQueue<>((p1, p2) -> p1[0] != p2[0] ? p2[0] - p1[0] : p2[1] - p1[1]);
        for (int i = 0; i < k; i++) {
            queue.offer(new int[]{nums[i], i});
        }
        int[] ans = new int[n - k + 1];
        // 将第一次答案加入数据
        ans[0] = queue.peek()[0];
        for (int i = k; i < n; i++) {
            // 将新元素加入优先队列
            queue.offer(new int[]{nums[i], i});
            // 循环判断当前队首是否在窗口中，窗口的左边界为i-k
            while (queue.peek()[1] <= i - k) {
                queue.poll();
            }
            // 在窗口中直接赋值即可
            ans[i - k + 1] = queue.peek()[0];
        }
        return ans;
    }

    // 单调栈
    public int[] maxSlidingWindow3(int[] nums, int k) {
        int idx = 0;
        int[] ret = new int[nums.length - k + 1];
        // 借助单调栈 在O(1)时间内找到最大值
        Deque<Integer> stack = new ArrayDeque<>();
        for (; idx < k; idx++) {
            while (!stack.isEmpty() && stack.getLast() < nums[idx]) {
                stack.removeLast();
            }
            stack.addLast(nums[idx]);
        }
        ret[0] = stack.getFirst();
        for (; idx < nums.length; idx++) {
            // 删除元素是最大值时 从栈移除该元素
            // 栈中比该元素靠后的元素都是窗口中的元素 不可能出现窗口外元素在栈中
            // 因为 单调栈同时保证了 大小顺序 和 先后顺序
            if (nums[idx - k] == stack.getFirst()) {
                stack.removeFirst();
            }
            // 该元素之前元素 且比该元素小 将会删除
            while (!stack.isEmpty() && stack.getLast() < nums[idx]) {
                stack.removeLast();
            }
            stack.addLast(nums[idx]);
            ret[idx - k + 1] = stack.getFirst();
        }
        return ret;
    }

    // 分组
    // 把 nums 分为多个 k 个组，那么任意 k 窗口范围只有两个情况，要么刚好就是分块，要不就是横跨两个分块
    public int[] maxSlidingWindow4(int[] nums, int k) {
        int len = nums.length;
        int[] prefixMax = new int[len];
        int[] suffixMax = new int[len];
        for (int i = 0; i < len; i++) {
            if (i % k == 0) {
                prefixMax[i] = nums[i];
            } else {
                prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
            }
        }
        for (int i = len - 1; i >= 0; i--) {
            if (i == len - 1 || (i + 1) % k == 0) {
                suffixMax[i] = nums[i];
            } else {
                suffixMax[i] = Math.max(suffixMax[i + 1], nums[i]);
            }
        }
        int[] ans = new int[len - k + 1];
        // 两组 每一组截取一部分 加起来长度是k
        for (int i = 0; i <= len - k; ++i) {
            //      组 0             组 1
            // [0...(i...k-1)][(k...i+k-1)...2k-1]
            //    [suffix          prefix]
            // [i, i+k-1] 长度为k
            ans[i] = Math.max(prefixMax[i + k - 1], suffixMax[i]);
        }
        return ans;
    }
}
