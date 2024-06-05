package normal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: MaximalRectangle
 * @Description: 85. 最大矩形
 * @Author: zhaooo
 * @Date: 2023/11/14 10:18
 */
public class MaximalRectangle {
    public int maximalRectangle(char[][] matrix) {
        int ans = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        // 单调增栈
        Deque<Integer> stack = new ArrayDeque<>();
        // height是每一排的当前高度
        // heights最后一位为0
        int[] heights = new int[n + 2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    heights[j + 1]++;
                } else {
                    heights[j + 1] = 0;
                }
            }
            // 算法同84. 柱状图中最大的矩形（LargestRectangleInHistogram）
            stack.clear();
            stack.addLast(0);
            for (int j = 1; j < n + 2; j++) {
                while (heights[stack.getLast()] > heights[j]) {
                    int height = heights[stack.removeLast()];
                    ans = Math.max(ans, (j - stack.getLast() - 1) * height);
                }
                stack.addLast(j);
            }
        }
        return ans;
    }
}
