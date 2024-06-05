package normal;

/**
 * @ClassName: MaxConsecutiveOnesIII
 * @Description: 1004. 最大连续1的个数 III
 * @Author: zhaooo
 * @Date: 2023/09/30 15:04
 */
public class MaxConsecutiveOnesIII {
    public int longestOnes(int[] nums, int k) {
        int l = 0, count = 0, ans = 0;
        for (int r = 0; r < nums.length; r++) {
            if (nums[r] == 0) {
                count++;
            }
            while (count > k) {
                if (nums[l++] == 0) {
                    count--;
                }
            }
            ans = Math.max(r - l + 1, ans);
        }
        return ans;
    }
}
