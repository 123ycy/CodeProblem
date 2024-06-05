package normal;

/**
 * @ClassName: BitwiseAndOfNumbersRange
 * @Description: 201. 数字范围按位与
 * @Author: zhaooo
 * @Date: 2023/8/30/9:32
 */
public class BitwiseAndOfNumbersRange {
    /**
     * 找到的一个不同的位
     *
     * @param left
     * @param right
     * @return
     */
    public int rangeBitwiseAnd(int left, int right) {
        int result = 0;
        for (int i = 31; i >= 0; i--) {
            int mask = 1 << i;
            if ((left & mask) == (right & mask)) {
                result |= (left & mask);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * 右移找到公共前缀，再左移恢复结果
     *
     * @param m
     * @param n
     * @return
     */
    public int rangeBitwiseAnd2(int m, int n) {
        int shift = 0;
        // 找到公共前缀
        while (m < n) {
            m >>= 1;
            n >>= 1;
            ++shift;
        }
        return m << shift;
    }

    /**
     * Brian Kernighan 算法，清除二进制串中最右边的 1
     *
     * @param m
     * @param n
     * @return
     */
    public int rangeBitwiseAnd3(int m, int n) {
        while (m < n) {
            // 抹去最右边的 1
            n = n & (n - 1);
        }
        return n;
    }
}
