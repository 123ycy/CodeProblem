package normal;

/**
 * @ClassName: RemoveDuplicatesFromSortedArray
 * @Description: 26. 删除有序数组中的重复项
 * @Author: zhaooo
 * @Date: 2023/06/05 20:22
 */
public class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        int idx = 1, l = 0;
        for (; idx < len; idx++) {
            if (nums[idx] != nums[l]) {
                nums[++l] = nums[idx];
            }
        }
        // 返回的是长度不是下标
        return l + 1;
    }

    // 为了让解法更具有一般性，我们将原问题的「最多保留 1 位」修改为「最多保留 k 位」。
    public int removeDuplicates2(int[] nums) {
        return process(nums, 1);
    }
    int process(int[] nums, int k) {
        int idx = 0;
        for (int x : nums) {
            // idx < k表示前k个直接赋值
            // nums[idx - k] == x 时会跳过该元素
            if (idx < k || nums[idx - k] != x) nums[idx++] = x;
        }
        return idx;
    }
}
