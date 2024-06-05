package normal;

import java.util.Arrays;

/**
 * @ClassName: CherryPickup
 * @Description: 741. 摘樱桃
 * @Author: zhaooo
 * @Date: 2022/7/10/10:08
 */
public class CherryPickup {

    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][][] f = new int[n * 2 - 1][n][n];
        for (int[][] i : f) {
            for (int[] j : i) {
                Arrays.fill(j, Integer.MIN_VALUE);
            }
        }
        // f[k][x1][x2]
        // k表示当前前进的步数，A的坐标为[x1,k-x1]，B的坐标为[x2,k-x2]
        f[0][0][0] = grid[0][0];
        for (int k = 1; k < n * 2 - 1; ++k) {
            for (int x1 = Math.max(k - n + 1, 0); x1 <= Math.min(k, n - 1); ++x1) {
                int y1 = k - x1;
                if (grid[x1][y1] == -1) {
                    continue;
                }
                for (int x2 = x1; x2 <= Math.min(k, n - 1); ++x2) {
                    int y2 = k - x2;
                    if (grid[x2][y2] == -1) {
                        continue;
                    }
                    // 都往右
                    int res = f[k - 1][x1][x2];
                    if (x1 > 0) {
                        // 往下，往右
                        res = Math.max(res, f[k - 1][x1 - 1][x2]);
                    }
                    if (x2 > 0) {
                        // 往右，往下
                        res = Math.max(res, f[k - 1][x1][x2 - 1]);
                    }
                    if (x1 > 0 && x2 > 0) {
                        // 都往下
                        res = Math.max(res, f[k - 1][x1 - 1][x2 - 1]);
                    }
                    res += grid[x1][y1];
                    if (x2 != x1) {
                        // 避免重复摘同一个樱桃
                        res += grid[x2][y2];
                    }
                    f[k][x1][x2] = res;
                }
            }
        }
        return Math.max(f[n * 2 - 2][n - 1][n - 1], 0);
    }

    public int cherryPickup2(int[][] grid) {
        int n = grid.length;
        int[][] f = new int[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(f[i], Integer.MIN_VALUE);
        }
        f[0][0] = grid[0][0];
        for (int k = 1; k < n * 2 - 1; ++k) {
            //由于f[k][][]都是从f[k-1][][]转移过来的，我们可以通过倒序循环x1和x2，来优化掉第一个维度。
            // 由大到小，小的还是上一轮的结果，不会重复使用
            for (int x1 = Math.min(k, n - 1); x1 >= Math.max(k - n + 1, 0); --x1) {
                // 简化情况，令A在B上方，即x2>=x1
                for (int x2 = Math.min(k, n - 1); x2 >= x1; --x2) {
                    int y1 = k - x1, y2 = k - x2;
                    if (grid[x1][y1] == -1 || grid[x2][y2] == -1) {
                        f[x1][x2] = Integer.MIN_VALUE;
                        continue;
                    }
                    int res = f[x1][x2];
                    if (x1 > 0) {
                        res = Math.max(res, f[x1 - 1][x2]);
                    }
                    if (x2 > 0) {
                        res = Math.max(res, f[x1][x2 - 1]);
                    }
                    if (x1 > 0 && x2 > 0) {
                        res = Math.max(res, f[x1 - 1][x2 - 1]);
                    }
                    res += grid[x1][y1];
                    if (x2 != x1) {
                        res += grid[x2][y2];
                    }
                    f[x1][x2] = res;
                }
            }
        }
        return Math.max(f[n - 1][n - 1], 0);
    }
}
