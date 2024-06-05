package normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: PermutationsII
 * @Description: 47. 全排列 II
 * @Author: zhaooo
 * @Date: 2024/04/06 22:42
 */
public class PermutationsII {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        // 排序保证相同的数是连着的
        Arrays.sort(nums);
        // 初始化tmp长度到len
        for (int i = 0; i < nums.length; i++) {
            tmp.add(0);
        }
        dfs(nums, 0, 0, tmp, ans);
        return ans;
    }

    void dfs(int[] nums, int visited, int idx, List<Integer> tmp, List<List<Integer>> ans) {
        if (idx == nums.length) {
            ans.add(new ArrayList<>(tmp));
            return;
        }
        int pre = -100;
        for (int i = 0; i < nums.length; i++) {
            // 保证在填第 idx 个数的时候重复数字只会被填入一次即可
            // 可以理解为for是树的横向，dfs是树的纵向
            if (nums[i] == pre || (visited & (1 << i)) > 0) {
                continue;
            }
            pre = nums[i];
            // 不用回溯 tmp[idx]会一直修改
            tmp.set(idx, nums[i]);
            dfs(nums, visited | (1 << i), idx + 1, tmp, ans);
        }
    }
}
