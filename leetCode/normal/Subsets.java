package normal;

import java.util.*;

/**
 * @ClassName: Subsets
 * @Description: 78. 子集
 * @Author: zhaooo
 * @Date: 2023/12/29 16:41
 */
public class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < 1 << nums.length; i++) {
            List<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < nums.length; j++) {
                if (((1 << j) & i) > 0) {
                    tmp.add(nums[j]);
                }
            }
            ret.add(tmp);
        }
        return ret;
    }

    List<Integer> t = new ArrayList<>();
    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> subsets2(int[] nums) {
        dfs(0, nums);
        return ans;
    }

    // 只能添加比下标比cur大的 防止重复
    public void dfs(int cur, int[] nums) {
        if (cur == nums.length) {
            ans.add(new ArrayList<>(t));
            return;
        }
        // 是否添加nums[cur] 2种情况
        t.add(nums[cur]);
        dfs(cur + 1, nums);
        t.remove(t.size() - 1);
        dfs(cur + 1, nums);
    }
}
