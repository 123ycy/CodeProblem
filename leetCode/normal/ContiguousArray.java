package normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ContiguousArray
 * @Description: 525. 连续数组
 * @Author: zhaooo
 * @Date: 2023/10/09 15:22
 */
public class ContiguousArray {
    /**
     * 前缀和 + hashmap
     *
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        int ans = 0;
        int zeroDiffOne = 0;
        Map<Integer, Integer> map = new HashMap<>();
        // 初始化
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            zeroDiffOne += nums[i] == 0 ? 1 : -1;
            // get的结果是第一个zeroDiffOne出现的位置，令其为idx。
            // 数组为...,idx,[idx+1,...,i]，长度为i-(idx+1)+1=i-idx
            ans = Math.max(ans, i - map.getOrDefault(zeroDiffOne, i));
            // 保留第一个出现的位置，保证数组最长
            map.putIfAbsent(zeroDiffOne, i);
        }
        return ans;
    }
}
