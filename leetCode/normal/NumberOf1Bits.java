package normal;

/**
 * @ClassName: NumberOf1Bits
 * @Description: 191. 位1的个数
 * @Author: zhaooo
 * @Date: 2023/08/26 09:56
 */
public class NumberOf1Bits {
    /**
     * 循环
     *
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            res += n & 1;
            n >>>= 1;
        }
        return res;
    }

    /**
     * 位运算
     * n & (n - 1) 可以把 n 的二进制中，最后一个出现的 1 改写成 0。
     *
     * @param n
     * @return
     */
    public int hammingWeight2(int n) {
        int res = 0;
        while (n != 0) {
            res += 1;
            n &= n - 1;
        }
        return res;
    }
}

