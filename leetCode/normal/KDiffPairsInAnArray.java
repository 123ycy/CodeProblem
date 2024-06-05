package normal;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @ClassName: KDiffPairsInAnArray
 * @Description: 532. 数组中的 k-diff 数对
 * @Author: zhaooo
 * @Date: 2022/6/16/12:20
 */
public class KDiffPairsInAnArray {
    public int findPairs(int[] nums, int k) {
        int res = 0;
        if (k == 0) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int num : nums) {
                int count = map.getOrDefault(num, 0);
                if (count == 1) {
                    res++;
                }
                map.put(num, count + 1);
            }
            return res;
        }
        Arrays.sort(nums);
        for (int l = 0, r = 0; r < nums.length; ) {
            if (nums[r] - nums[l] < k) {
                r++;
            } else if (nums[r] - nums[l] > k) {
                l++;
            } else {
                res++;
                l++;
                while (nums[l] == nums[l - 1]) {
                    l++;
                }
            }
        }
        return res;
    }
}
