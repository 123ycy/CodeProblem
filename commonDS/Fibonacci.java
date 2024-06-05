/**
 * @ClassName: Fibonacci
 * @Description: 第n个斐波那契数
 * @Author: zhaooo
 * @Date: 2022/7/9/12:10
 */
public class Fibonacci {
    /**
     * 递归
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n < 2) {
            return n;
        }
        return fib(n - 2) + fib(n - 1);
    }

    /**
     * dp
     * @param n
     * @return
     */
    public int fib2(int n) {
        if (n < 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 2] + dp[i - 1];
        }
        return dp[n];
    }

    /**
     * 滚动数组
     * @param n
     * @return
     */
    public int fib3(int n) {
        if (n < 2) {
            return n;
        }
        int pre1 = 0, pre2 = 1, cur = 1;
        // i表示当前cur是第几个结果
        for (int i = 2; i <= n; i++) {
            pre2 = pre1;
            pre1 = cur;
            cur = pre1 + pre2;
        }
        return cur;
    }
}
