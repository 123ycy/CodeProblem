package normal;

import java.util.*;

/**
 * @ClassName: FindKPairsWithSmallestSums
 * @Description: 373. 查找和最小的 K 对数字
 * @Author: zhaooo
 * @Date: 2023/12/14 22:49
 */
public class FindKPairsWithSmallestSums {

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int n = nums1.length, m = nums2.length;
        List<List<Integer>> ans = new ArrayList<>();
        // 小根堆
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(ints -> (nums1[ints[0]] + nums2[ints[1]])));
        // 先添加[nums1[i], nums[0]]的全部元素，这样在队列add时只需要添加new int[]{a, b + 1}，不需要添加a改变的元素，避免重复添加
        for (int i = 0; i < Math.min(n, k); i++) q.add(new int[]{i, 0});
        while (ans.size() < k && !q.isEmpty()) {
            int[] poll = q.poll();
            int a = poll[0], b = poll[1];
            ans.add(new ArrayList<Integer>() {{
                add(nums1[a]);
                add(nums2[b]);
            }});
            // 添加比当前结果大的改变的nums2
            if (b + 1 < m) q.add(new int[]{a, b + 1});
        }
        return ans;
    }
}
