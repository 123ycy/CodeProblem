package normal;

/**
 * @ClassName: ConsecutiveNumbersSum
 * @Description: 829. 连续整数求和
 * @Author: zhaooo
 * @Date: 2022/6/3/10:06
 */
public class ConsecutiveNumbersSum {
    /**
     * 数学
     * @param n
     * @return
     */
    public int consecutiveNumbersSum(int n) {
        int res = 0;
        //(n - (i * (i - 1)) / 2) / n >= 1 等价于 i * (i + 1) <= 2 * n
        for (int i = 1; i * (i + 1) <= 2 * n; i++) {
            if ((n - (i * (i - 1)) / 2) % i == 0) {
                res++;
            }
        }
        return res;
    }

    /**
     * 优化
     * 以9=4+5=2+3+4为例,i表示有几项之和为N
     * 长为1，找9，n-1变成8
     * 长为2，找4, 5，通过上面的-1操作，我们实际找的是[4,4]（把最后1个数削成4），n-2变成6
     * 长为3，找2,3,4，实际找的是[2,2,2]（把最后两个数削成2）
     * @param n
     * @return
     */
    public int consecutiveNumbersSum2(int n) {
        int res = 0;
        for (int i = 1; n > 0; n -= i++) {
            res += (n % i == 0 ? 1 : 0);
        }
        return res;
    }
}
