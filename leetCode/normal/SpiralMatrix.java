package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SpiralMatrix
 * @Description: 54. 螺旋矩阵
 * https://leetcode.cn/problems/spiral-matrix/solutions/275716/shou-hui-tu-jie-liang-chong-bian-li-de-ce-lue-kan-/?orderBy=most_votes
 * @Author: zhaooo
 * @Date: 2023/04/13 19:15
 */
public class SpiralMatrix {
    /**
     * 一次循环1条边
     * 两条边连接点算归上条边
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        // j = -1表示从上一个循环结束开始
        int top = 0, bottom = m - 1, left = 0, right = n - 1, i = 0, j = -1;
        // dir[][0]对应i dir[][j]对应j
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Integer> ret = new ArrayList<>(m * n);
        while (true) {
            for (int[] dir : dirs) {
                // 对j修改
                if (dir[0] == 0) {
                    // 从上条直线末尾变为本条直线开始
                    j += dir[1];
                    while (left <= j && j <= right) {
                        ret.add(matrix[i][j]);
                        j += dir[1];
                    }
                    // 退出时 j>right或者j<left
                    j -= dir[1];
                    // 修改边界
                    top += dir[1] > 0 ? 1 : 0;
                    bottom += dir[1] < 0 ? -1 : 0;
                } else {
                    // 对i修改
                    // 从上条直线末尾变为本条直线开始
                    i += dir[0];
                    while (top <= i && i <= bottom) {
                        ret.add(matrix[i][j]);
                        i += dir[0];
                    }
                    i -= dir[0];
                    left += dir[0] < 0 ? 1 : 0;
                    right += dir[0] > 0 ? -1 : 0;
                }
                // 每次添加一条边判断是否结束
                if (ret.size() == m * n) {
                    return ret;
                }
            }
        }
    }

    /**
     * 一次循环4条边
     * 两条边连接点算归上条边
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder2(int[][] matrix) {
        // current four edge of the matrix, included
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        List<Integer> res = new ArrayList<>();
        while (true) {
            // 遍历top边
            for (int i = left; i <= right; i++) res.add(matrix[top][i]);
            // 修改top边同时判断是否最后一条边（此时为top边）已经遍历
            if (++top > bottom) break;
            for (int i = top; i <= bottom; i++) res.add(matrix[i][right]);
            if (--right < left) break;
            for (int i = right; i >= left; i--) res.add(matrix[bottom][i]);
            if (--bottom < top) break;
            for (int i = bottom; i >= top; i--) res.add(matrix[i][left]);
            if (++left > right) break;
        }
        return res;
    }
}
