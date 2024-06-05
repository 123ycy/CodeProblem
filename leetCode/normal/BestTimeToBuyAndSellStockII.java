package normal;

/**
 * @ClassName: BestTimeToBuyAndSellStockII
 * @Description: 122. 买卖股票的最佳时机 II
 * @Author: zhaooo
 * @Date: 2024/01/30 12:54
 */
public class BestTimeToBuyAndSellStockII {
    /**
     * dp
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        // dp[i][0]表示当前未持股 手中金额
        // dp[i][1]表示当前持股 手中金额
        int[][] dp = new int[len][2];
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], prices[i] + dp[i - 1][1]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[len - 1][0];
    }

    /**
     * 贪心
     * 贪心算法只能用于计算最大利润，计算的过程并不是实际的交易过程。
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int ans = 0;
        int n = prices.length;
        for (int i = 1; i < n; ++i) {
            ans += Math.max(0, prices[i] - prices[i - 1]);
        }
        return ans;
    }
}
