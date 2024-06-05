package normal;

/**
 * @ClassName: BestTimeToBuyAndSellStock
 * @Description: 121. 买卖股票的最佳时机
 * @Author: zhaooo
 * @Date: 2024/01/30 10:28
 */
public class BestTimeToBuyAndSellStock {
    /**
     * 一次遍历（优化的dp）
     * 相当于每次从新minCost开始判断
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int cost = Integer.MAX_VALUE, profit = 0;
        for (int price : prices) {
            cost = Math.min(cost, price);
            profit = Math.max(profit, price - cost);
        }
        return profit;
    }

    /**
     * 动态规划
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int ret = 0;
        // dp[i]表示在第i天卖出的最大利润
        int[] dp = new int[prices.length];
        for (int i = 1; i < prices.length; i++) {
            int dif = prices[i] - prices[i - 1];
            if (dif >= 0) {
                dp[i] = dp[i - 1] + dif;
                ret = Math.max(ret, dp[i]);
            } else {
                // dp[i]为0时表示不卖出，或者当天买入，当天卖出。此时的price小于之前买入的price
                dp[i] = Math.max(0, dp[i - 1] + dif);
            }
        }
        return ret;
    }
}