package normal;

/**
 * @ClassName: IntegerBreak
 * @Description: 343. 整数拆分
 * @Author: zhaooo
 * @Date: 2024/04/09 23:55
 */
public class IntegerBreak {
    // 动态规划
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                // 分为2个数时是否是最大值
                dp[i] = Math.max(dp[i], (i - j) * j);
                // 分为多个数时是否为最大值
                dp[i] = Math.max(dp[i], dp[i - j] * j);
            }
        }
        return dp[n];
    }

    // 数学
    public int integerBreak2(int n) {
        if (n <= 3) return n - 1;
        // 当由最多3构成时最大
        int a = n / 3, b = n % 3;
        if (b == 0) return (int) Math.pow(3, a);
        if (b == 1) return (int) Math.pow(3, a - 1) * 4;
        return (int) Math.pow(3, a) * 2;
    }

}
