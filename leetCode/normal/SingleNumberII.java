package normal;

/**
 * @ClassName: SingleNumberII
 * @Description: 137. 只出现一次的数字 II
 * @Author: zhaooo
 * @Date: 2023/08/27 10:25
 */
public class SingleNumberII {
    /**
     * 位运算
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            ones = ones ^ num & ~twos;
            twos = twos ^ num & ~ones;
        }
        return ones;
    }

    /**
     * 遍历统计
     *
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {
        int[] counts = new int[32];
        for (int num : nums) {
            for (int j = 0; j < 32; j++) {
                counts[j] += num & 1;
                num >>>= 1;
            }
        }
        int res = 0, m = 3;
        for (int i = 0; i < 32; i++) {
            res <<= 1;
            res |= counts[31 - i] % m;
        }
        return res;
    }
}
