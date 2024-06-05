package normal;

/**
 * @ClassName: Sqrtx
 * @Description: 69. x 的平方根
 * @Author: zhaooo
 * @Date: 2023/8/31/9:19
 */
public class Sqrtx {

    /**
     * 用指数函数 exp 和对数函数 ln 代替平方根函数
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }
        int ans = (int) Math.exp(0.5 * Math.log(x));
        return (long) (ans + 1) * (ans + 1) <= x ? ans + 1 : ans;
    }

    /**
     * 二分查找
     *
     * @param x
     * @return
     */
    public int mySqrt2(int x) {
        int i = 1;
        int j = x;
        int ans = 0;
        // i <= j 表示左右都有可能是结果
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (mid * mid == x) {
                return mid;
            }
            // upper bound的形式，因为我们要找的ans要是最接近于x的最大的数，利用upper bound
            if (mid <= x / mid) {
                i = mid + 1;
                // 只要mid <= x/mid，left左边界就会一直向右移动，ans就会一直更新（变大），直到不在满足mid <= x/mid的条件为止，ans就会停止更新，
                // 永远停在满足mid<=x/mid条件下面的最后一次更新，即满足ans * ans <= mid的最大值。
                ans = mid;
            } else {
                j = mid - 1;
            }
        }
        return ans;
    }

    /**
     * 牛顿迭代法
     *
     * @param x
     * @return
     */
    public int mySqrt3(int x) {
        if (x == 0) {
            return 0;
        }
        double C = x, x0 = x;
        while (true) {
            double xi = 0.5 * (x0 + C / x0);
            // 相邻两次迭代的结果的差值是否小于一个极小的非负数ϵ，就可以表示结束
            if (Math.abs(x0 - xi) < 1e-7) {
                break;
            }
            x0 = xi;
        }
        return (int) x0;
    }
}
