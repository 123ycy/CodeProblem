package normal;

import java.util.List;

/**
 * @ClassName: Triangle
 * @Description: 120. 三角形最小路径和
 * @Author: zhaooo
 * @Date: 2023/09/03 11:12
 */
public class Triangle {
    List<List<Integer>> triangle;
    int ans = Integer.MAX_VALUE;

    /**
     * dfs 超时
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 1) {
            return triangle.get(0).get(0);
        }
        this.triangle = triangle;
        dfs(0, 0, 0);
        return ans;
    }

    void dfs(int depth, int sum, int idx) {
        List<Integer> list = triangle.get(depth);
        if (depth == triangle.size() - 1) {
            ans = Math.min(ans, sum + list.get(idx));
            return;
        }
        dfs(depth + 1, sum + list.get(idx), idx);
        dfs(depth + 1, sum + list.get(idx), idx + 1);
    }

    /**
     * 动态规划
     *
     * @param triangle
     * @return
     */
    public int minimumTotal2(List<List<Integer>> triangle) {
        if (triangle.size() == 1) {
            return triangle.get(0).get(0);
        }
        int len = triangle.size();
        // 设置长度为len+1，防止访问最后一排时溢出，不用初始化dp数组
        int[][] dp = new int[len + 1][len + 1];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];
    }

    /**
     * dp+滚动数组
     *
     * @param triangle
     * @return
     */
    public int minimumTotal3(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                // dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j)  只和dp[i + 1]有关
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }
}
