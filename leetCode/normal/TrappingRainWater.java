package normal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: TrappingRainWater
 * @Description: 42. 接雨水
 * @Author: zhaooo
 * @Date: 2024/02/18 11:06
 */
public class TrappingRainWater {
    /**
     * 构造2个辅助数组
     * 每个柱子能盛水的深度，取决于min(左边最高，右边最高），即res[i] = min(l[i], r[i]) - height[i];
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int ans = 0;
        int len = height.length;
        // l[i]表示l的i的左侧最高
        int[] l = new int[len], r = new int[len];
        int maxL = 0, maxR = 0;
        for (int i = 0; i < len; i++) {
            l[i] = maxL;
            r[len - 1 - i] = maxR;
            maxL = Math.max(maxL, height[i]);
            maxR = Math.max(maxR, height[len - 1 - i]);
        }
        for (int i = 0; i < len; i++) {
            int min = Math.min(l[i], r[i]);
            if (min > height[i]) {
                ans += (min - height[i]);
            }
        }
        return ans;
    }

    /**
     * 双指针
     * @param height
     * @return
     */
    public int trap2(int[] height) {
        int ans = 0;
        // l, r分别表示将要计算的下标
        int l = 0, r = height.length - 1;
        // lMax i以及左边所有元素的最大值
        // rMax j以及右边所有元素的最大值
        int lMax = 0, rMax = 0;
        // l > r 才遍历完所有下标
        while (l <= r) {
            lMax = Math.max(lMax, height[l]);
            rMax = Math.max(rMax, height[r]);
            // 每次移动较低一侧 保证 不会加负数
            // 左侧较低
            if (height[l] < height[r]) {
                // min(lMax, rMax) >= height[i]
                ans += Math.min(lMax, rMax) - height[l++];
                // 右侧较低
            } else {
                ans += Math.min(lMax, rMax) - height[r++];
            }
        }
        return ans;
    }

    /**
     * 单调栈 单调减栈
     * @param height
     * @return
     */
    public int trap3(int[] height) {
        int ans = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peekLast()]) {
                int cur = stack.removeLast();
                // 栈空时 不能储存雨水 当前height[i]作为新边界
                if (stack.isEmpty()) {
                    break;
                }
                // i - stack.peekLast() - 1：宽度
                // Math.min(height[i], height[stack.peekLast()]) - height[cur]：高度
                // 相当于上涨[stack.peekLast(), i]之间的柱子 高度统一到min 原高度是height[cur]
                ans += (i - stack.peekLast() - 1) * (Math.min(height[i], height[stack.peekLast()]) - height[cur]);
            }
            stack.addLast(i);
        }
        return ans;
    }
}
