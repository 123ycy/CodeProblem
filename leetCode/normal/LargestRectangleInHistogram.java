package normal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: LargestRectangleInHistogram
 * @Description: 84. 柱状图中最大的矩形
 * 遍历每个高度，是要以当前高度为基准，寻找最大的宽度 组成最大的矩形面积
 * 那就是要找左边第一个小于当前高度的下标left，再找右边第一个小于当前高度的下标right 那宽度就是这两个下标之间的距离了
 * https://leetcode.cn/problems/largest-rectangle-in-histogram/solutions/142012/bao-li-jie-fa-zhan-by-liweiwei1419/
 * @Author: zhaooo
 * @Date: 2023/11/12 16:01
 */
public class LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return heights[0];
        }
        // 添加哨兵 优化边界判断
        int[] newHeights = new int[len + 2];
        System.arraycopy(heights, 0, newHeights, 1, len);
        int result = 0;
        Deque<Integer> stack = new ArrayDeque<>(len + 2);
        stack.addLast(0);
        for (int i = 1; i < len + 2; i++) {
            while (newHeights[i] < newHeights[stack.getLast()]) {
                int curHeight = newHeights[stack.removeLast()];
                int curWidth = i - stack.getLast() - 1;
                result = Math.max(result, curHeight * curWidth);
            }
            stack.addLast(i);
        }
        return result;
    }
}
