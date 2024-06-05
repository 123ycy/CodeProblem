package normal;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @ClassName: RotateArray
 * @Description: 189. 轮转数组
 * @Author: zhaooo
 * @Date: 2023/04/19 17:14
 */
public class RotateArray {
    /**
     * 暴力
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        k %= len;
        int[] newNums = new int[len];
        /**
         * 使用native方法可以优化到击败100%
         * if (len - k >= 0) System.arraycopy(nums, 0, newNums, k, len - k);
         * if (k >= 0) System.arraycopy(nums, len - k, newNums, 0, k);
         * System.arraycopy(newNums, 0, nums, 0, nums.length);
         */
        for (int i = k; i < len; i++) {
            newNums[i] = nums[i - k];
        }
        for (int i = 0; i < k; i++) {
            newNums[i] = nums[len - k + i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = newNums[i];
        }
    }

    /**
     * 原地旋转
     * 链接：https://leetcode.cn/problems/rotate-array/solutions/683855/shu-zu-fan-zhuan-xuan-zhuan-shu-zu-by-de-5937/
     * @param nums
     * @param k
     */
    public void rotate2(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }
    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start += 1;
            end -= 1;
        }
    }
}
