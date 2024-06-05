package normal;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: Permutations
 * @Description: 46. 全排列
 * 可以看作对树的访问
 * @Author: zhaooo
 * @Date: 2024/02/05 12:56
 */
public class Permutations {
    List<List<Integer>> ret = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        dfs(nums, 0, new Integer[nums.length], 0);
        return ret;
    }

    void dfs(int[] nums, int idx, Integer[] tmp, int map) {
        if (idx == nums.length) {
            ret.add(Arrays.stream(tmp).collect(Collectors.toList()));
        }
        for (int i = 0; i < nums.length; i++) {
            if ((map & 1 << i) == 0) {
                tmp[idx] = nums[i];
                dfs(nums, idx + 1, tmp, map | 1 << i);
            }
            // 不需要回溯，map已经标定了每个dfs哪些可以访问，对应idx直接覆盖就行
        }
    }

    // bfs/循环
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        // 记录访问情况
        Deque<Integer> visDeque = new ArrayDeque<>();
        // 记录当前列表
        Deque<List<Integer>> numDeque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> tmp = new ArrayList<>();
            tmp.add(nums[i]);
            visDeque.addLast(1 << i);
            numDeque.addLast(tmp);
        }
        while (!visDeque.isEmpty()) {
            int vis = visDeque.removeFirst();
            List<Integer> cur = numDeque.removeFirst();
            if (cur.size() == nums.length) {
                ans.add(cur);
                continue;
            }
            for (int i = 0; i < nums.length; i++) {
                if ((vis & (1 << i)) == 0) {
                    List<Integer> tmp = new ArrayList<>(cur);
                    tmp.add(nums[i]);
                    visDeque.addLast(vis | (1 << i));
                    numDeque.addLast(tmp);
                }
            }
        }
        return ans;
    }
}
