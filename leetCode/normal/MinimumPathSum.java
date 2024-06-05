package normal;

/**
 * @ClassName: MinimumPathSum
 * @Description: 64. 最小路径和
 * @Author: zhaooo
 * @Date: 2024/05/03 22:25
 */
public class MinimumPathSum {
    /**
     * 动态规划
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        int tmp = 0;
        for (int i = 0; i < m; i++) {
            tmp += grid[i][0];
            dp[i][0] = tmp;
        }
        tmp = 0;
        for (int i = 0; i < n; i++) {
            tmp += grid[0][i];
            dp[0][i] = tmp;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }


    /**
     * 一维dp数组
     * @param grid
     * @return
     */
    public int minPathSum2(int[][] grid) {
        int[] dp = new int[grid[0].length];
        int tmp = 0;
        for (int i = 0; i < grid[0].length; i++) {
            tmp += grid[0][i];
            dp[i] = tmp;
        }
        for (int i = 1; i < grid.length; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < grid[0].length; j++) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
            }
        }
        return dp[grid[0].length - 1];
    }
}
