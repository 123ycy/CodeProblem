package pointSwordAtOfferII;

/**
 * @ClassName: T091
 * @Description: 剑指 Offer II 091. 粉刷房子
 * @Author: zhaooo
 * @Date: 2022/6/25/10:38
 */
public class T091 {
    /**
     * 暴力DFS超时
     * @param costs
     * @return
     */
    public int minCost(int[][] costs) {
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            res = Math.min(dfs(costs, i, 0), res);
        }
        return res;
    }

    public int dfs(int[][] costs, int chosen, int index) {
        if (index == costs.length) {
            return 0;
        }
        return costs[index++][chosen] + Math.min(dfs(costs, (++chosen) % 3, index), dfs(costs, (++chosen) % 3, index));
    }

    public int minCost2(int[][] costs) {
        int colorNum = 3;
        int len = costs.length;
        int res = Integer.MAX_VALUE;
        // dp[i][j]表示以以第i栋房子结尾粉刷成颜色j的花费
        int[][] dp = new int[len + 1][colorNum];
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < colorNum; j++) {
                // 初始化
                dp[i][j] = Integer.MAX_VALUE;
                // 由前一栋房子得出结果
                for (int k = 0; k < colorNum; k++) {
                    if (k == j) {
                        continue;
                    }
                    dp[i][j] = Math.min(dp[i - 1][k] + costs[i - 1][j], dp[i][j]);
                }
            }
        }
        for (int i = 0; i < colorNum; i++) {
            res = Math.min(res, dp[len][i]);
        }
        return res;
    }

    /**
     * dp优化
     * @param cs
     * @return
     */
    public int minCost3(int[][] cs) {
        int n = cs.length;
        int a = cs[0][0], b = cs[0][1], c = cs[0][2];
        for (int i = 0; i < n - 1; i++) {
            int d = Math.min(b, c) + cs[i + 1][0];
            int e = Math.min(a, c) + cs[i + 1][1];
            int f = Math.min(a, b) + cs[i + 1][2];
            a = d;
            b = e;
            c = f;
        }
        return Math.min(a, Math.min(b, c));
    }
}
