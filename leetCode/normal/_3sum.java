package normal;

import java.util.*;

/**
 * @ClassName: _3sum
 * @Description: 15. 三数之和
 * @Author: zhaooo
 * @Date: 2023/09/25 14:39
 */
public class _3sum {
    /**
     * 排序 + 双指针
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            // 同一个target跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1, r = nums.length - 1;
            int target = -nums[i];
            while (l < r) {
                // 同一个l
                if (l > i + 1 && nums[l] == nums[l - 1]) {
                    l++;
                    continue;
                }
                if (r < nums.length - 1 && nums[r] == nums[r + 1]) {
                    r--;
                    continue;
                }
                int sum = nums[l] + nums[r];
                if (sum < target) {
                    l++;
                } else if (sum > target) {
                    r--;
                } else {
                    res.add(new ArrayList<>(Arrays.asList(nums[i], nums[l], nums[r])));
                    l++;
                    r--;
                }
            }
        }
        return res;
    }

    /**
     * 排序 + map（2数之和）
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length <= 2) {
            return ans;
        }
        Arrays.sort(nums);
        int min = nums[0];
        int max = nums[nums.length - 1];
        // 记录num出现的次数
        int[] map = new int[max - min + 1];
        for (int num : nums) {
            map[num - min]++;
        }
        for (int i = 0; i < nums.length; i++) {
            map[nums[i] - min]--;
            // 同一个target跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i] > 0) {
                break;
            }
            int target = -nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                // 同一个中间num跳过，因为target已经确定，num[j]相同则另外一个也相同
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // nums[j] > target / 2 则2数之和大于target
                if (nums[j] > target / 2) {
                    break;
                }
                map[nums[j] - min]--;
                // 判断target - nums[j]在min和max之中，且剩下的数中有target - nums[j]
                if (min <= target - nums[j] && max >= target - nums[j] && map[target - nums[j] - min] > 0) {
                    ans.add(new ArrayList<>(Arrays.asList(nums[i], nums[j], target - nums[j])));
                }
                // 恢复
                map[nums[j] - min]++;
            }
        }
        return ans;
    }
}
