package normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ContainsDuplicateIII
 * @Description: 220. 存在重复元素 III
 * @Author: zhaooo
 * @Date: 2023/12/06 15:37
 */
public class ContainsDuplicateIII {
    /**
     * 桶
     *
     * @param nums
     * @param indexDiff
     * @param valueDiff
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        Map<Integer, Integer> buck = new HashMap<>();
        for (int i = 0; i < Math.min(indexDiff + 1, nums.length); i++) {
            int key = (int) ((nums[i] + 1e9)) / (valueDiff + 1);
            if (check(key, nums[i], valueDiff, buck)) {
                return true;
            }
            buck.put(key, nums[i]);
        }
        for (int i = indexDiff + 1; i < nums.length; i++) {
            int oldKey = (int) ((nums[i - indexDiff - 1] + 1e9)) / (valueDiff + 1);
            int newKey = (int) ((nums[i] + 1e9)) / (valueDiff + 1);
            buck.remove(oldKey);
            if (check(newKey, nums[i], valueDiff, buck)) {
                return true;
            }
            buck.put(newKey, nums[i]);
        }
        return false;
    }

    boolean check(int key, int value, int valueDiff, Map<Integer, Integer> buck) {
        if (buck.containsKey(key)) {
            return true;
        }
        if (buck.containsKey(key - 1) && (value - buck.get(key - 1)) <= valueDiff) {
            return true;
        }
        return buck.containsKey(key + 1) && (buck.get(key + 1) - value) <= valueDiff;
    }
}
