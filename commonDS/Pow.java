/**
 * @ClassName: Pow
 * @Description: 快速幂
 * @Author: zhaooo
 * @Date: 2024/03/31 16:53
 */
public class Pow {
    public long pow(int x, int n) {
        long ans = 1;
        while (n > 0) {
            // 是否乘以x
            if ((n & 1) == 1) {
                ans *= x;
            }
            x *= x;
            n >>= 1;
        }
        return ans;
    }
}
