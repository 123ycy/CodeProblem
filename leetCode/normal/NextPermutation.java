package normal;

import java.util.Arrays;

/**
 * @ClassName: NextPermutation
 * @Description: 31. 下一个排列
 * @Author: zhaooo
 * @Date: 2024/02/22 19:27
 */
public class NextPermutation {
    /**
     * 遍历
     * https://leetcode.cn/problems/next-permutation/solutions/80560/xia-yi-ge-pai-lie-suan-fa-xiang-jie-si-lu-tui-dao-
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        //                idx
        // [1, 2, 3, 8, 5, 7, 6, 4]
        int idx = nums.length - 1;
        while (idx > 0) {
            // 找到第一个升序
            if (nums[idx] > nums[idx - 1]) {
                int swapIdx = nums.length - 1;
                // 找到第一个大于nums[idx - 1]
                while (nums[swapIdx] <= nums[idx - 1]) {
                    swapIdx--;
                }
                int tmp = nums[idx - 1];
                nums[idx - 1] = nums[swapIdx];
                nums[swapIdx] = tmp;
                Arrays.sort(nums, idx, nums.length);
                return;
            }
            idx--;
        }
        // nums逆序
        Arrays.sort(nums);
    }
}
