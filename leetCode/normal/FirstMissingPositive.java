package normal;

/**
 * @ClassName: FirstMissingPositive
 * @Description: 41. 缺失的第一个正数
 * @Author: zhaooo
 * @Date: 2024/02/28 13:55
 */
public class FirstMissingPositive {
    /**
     * 对于一个长度为 N 的数组，其中没有出现的最小正整数只能在 [1,N+1] 中
     * 遍历一次数组把大于等于1的和小于数组大小的值放到原数组对应位置，然后再遍历一次数组查当前下标是否和值对应，如果不对应那这个下标就是答案，
     * 否则遍历完都没出现那么答案就是数组长度加1
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            // 不断交换num[i]中的元素放到对应下标
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }
        // 如果nums[i]!=i+1 表示该下标对应元素缺失 直接返回
        for (int i = 0; i < n; ++i) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
