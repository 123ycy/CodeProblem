package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SpiralMatrixII
 * @Description: 59. 螺旋矩阵 II
 * @Author: zhaooo
 * @Date: 2023/04/15 14:44
 */
public class SpiralMatrixII {
    /**
     * 同54. 螺旋矩阵
     *
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int[][] ret = new int[n][n];
        int top = 0, bottom = n - 1, left = 0, right = n - 1;
        int cur = 1;
        while (true) {
            // 遍历top边
            for (int i = left; i <= right; i++) ret[top][i] = cur++;
            // 修改top边同时判断是否最后一条边（此时为top边）已经遍历
            if (++top > bottom) break;
            for (int i = top; i <= bottom; i++) ret[i][right] = cur++;
            if (--right < left) break;
            for (int i = right; i >= left; i--) ret[bottom][i] = cur++;
            if (--bottom < top) break;
            for (int i = bottom; i >= top; i--) ret[i][left] = cur++;
            if (++left > right) break;
        }
        return ret;
    }
}
