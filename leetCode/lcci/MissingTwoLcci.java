package lcci;

import java.util.Arrays;

/**
 * @ClassName: MissingTwoLcci
 * @Description: 面试题 17.19. 消失的两个数字
 * @Author: zhaooo
 * @Date: 2022/9/26/10:35
 */
public class MissingTwoLcci {

    /**
     * 加法求和
     * @param nums
     * @return
     */
    public int[] missingTwo(int[] nums) {
        int n = nums.length + 2;
        long sum = Arrays.stream(nums).sum();
        int twoSum = (int) (n * (n + 1) / 2 - sum);
        int limit = twoSum / 2;
        sum = 0;
        for (int num : nums) {
            // 2个数一个小于limit一个大于limit
            if (num <= limit) {
                sum += num;
            }
        }
        int num1 = (int) (limit * (limit + 1) / 2 - sum);
        return new int[]{num1, twoSum - num1};
    }

    /**
     * 位运算
     * a xor a xor b = b
     * x&(-x)：保留二进制下最后出现的1的位置，其余位置置0（即一个数中最大的2的n次幂的因数）
     * x&(x-1)：消除二进制下最后出现1的位置，其余保持不变
     * https://www.cnblogs.com/yzxag/p/12668034.html
     * https://leetcode.cn/problems/missing-two-lcci/solution/xiao-shi-de-liang-ge-shu-zi-by-leetcode-zuwq3/
     * @param nums
     * @return
     */
    public int[] missingTwo2(int[] nums) {
        int xorSum = 0;
        int n = nums.length + 2;
        for (int num : nums) {
            xorSum ^= num;
        }
        for (int i = 1; i <= n; i++) {
            xorSum ^= i;
        }
        // 找到最低为1的位 防止溢出
        int lsb = (xorSum == Integer.MIN_VALUE ? xorSum : xorSum & (-xorSum));
        int type1 = 0, type2 = 0;
        // 分为2类 找到最低为1的位为1 找到最低为1的位为0
        for (int num : nums) {
            if ((num & lsb) != 0) {
                type1 ^= num;
            } else {
                type2 ^= num;
            }
        }
        for (int i = 1; i <= n; i++) {
            if ((i & lsb) != 0) {
                type1 ^= i;
            } else {
                type2 ^= i;
            }
        }
        return new int[]{type1, type2};
    }
}
