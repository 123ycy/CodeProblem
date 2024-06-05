package normal;

/**
 * @ClassName: UniquePathsII
 * @Description: 63. 不同路径 II
 * @Author: zhaooo
 * @Date: 2023/09/04 15:23
 */
public class UniquePathsII {
    int ans = 0;

    /**
     * dfs 超时
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        dfs(0, 0, obstacleGrid);
        return ans;
    }

    void dfs(int i, int j, int[][] obstacleGrid) {
        if (i >= obstacleGrid.length || j >= obstacleGrid[0].length || obstacleGrid[i][j] == 1) {
            return;
        }
        if (i == obstacleGrid.length - 1 && j == obstacleGrid[0].length - 1) {
            ans++;
            return;
        }
        dfs(i + 1, j, obstacleGrid);
        dfs(i, j + 1, obstacleGrid);
    }

    /**
     * 动态规划
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m + 1][n + 1];
        // 初始化dp数组
        // dp[1][0] = 1也行，因为第一行和第一列都无法修改
        dp[0][1] = 1;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (obstacleGrid[i - 1][j - 1] == 1) {
                    continue;
                }
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m][n];
    }
}
