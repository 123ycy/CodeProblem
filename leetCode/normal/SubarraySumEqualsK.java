package normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SubarraySumEqualsK
 * @Description: 560. 和为 K 的子数组
 * @Author: zhaooo
 * @Date: 2023/10/08 20:24
 */
public class SubarraySumEqualsK {
    /**
     * 前缀和 + hashmap
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        int ans = 0;
        int[] sum = new int[nums.length + 1];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        for (int i : sum) {
            ans += map.getOrDefault(i - k, 0);
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        return ans;
    }

    /**
     * 优化为单次循环
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum2(int[] nums, int k) {
        int ans = 0, sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        // 前0个元素之和为0
        map.put(0, 1);
        for (int num : nums) {
            sum += num;
            ans += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}
