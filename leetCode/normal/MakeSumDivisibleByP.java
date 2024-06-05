package normal;

import java.util.Arrays;

/**
 * @ClassName: minSubarray
 * @Description: 1590. 使数组和能被 P 整除
 * @Author: zhaooo
 * @Date: 2023/3/10/13:23
 */
class MakeSumDivisibleByP {
    /**
     * 前缀和
     * @param nums
     * @param p
     * @return
     */
    public int minSubarray(int[] nums, int p) {
        int len = nums.length;
        int cur = 0;
        long[] pres = new long[len + 1];
        for (int i = 1; i <= len; i++) {
            pres[i] = pres[i - 1] + nums[i - 1];
        }
        if (pres[len] < p) {
            return -1;
        }
        long remainder = pres[len] % p;
        if (remainder == 0) {
            return 0;
        }
        for (int i = 1; i < len; i++) {
            for (int j = len; j >= i; j--) {
                if ((pres[j] - pres[j - i]) % p == remainder) {
                    return i;
                }
            }
        }
        return -1;
    }
}