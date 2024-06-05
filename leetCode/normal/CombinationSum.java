package normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: CombinationSum
 * @Description: 39. 组合总和
 * @Author: zhaooo
 * @Date: 2024/04/30 13:07
 */
public class CombinationSum {
    /**
     * dfs
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, candidates, target, 0, new ArrayList<>());
        return res;
    }

    public void dfs(List<List<Integer>> res, int[] candidates, int target, int index, List<Integer> combine) {
        if (index == candidates.length) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(combine));
        }
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target) {
                break;
            }
            combine.add(candidates[i]);
            // 保证可以重复选择当前的数字
            dfs(res, candidates, target - candidates[i], i, combine);
            combine.remove(combine.size() - 1);
        }
    }

    /**
     * 动态规划数组倒推路径
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // dp[i][j]表示前i个数的和为j时组合总数
        int[][] dp = new int[candidates.length + 1][target + 1];
        for (int i = 0; i <= candidates.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= candidates.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (j - candidates[i - 1] >= 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - candidates[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        List<List<Integer>> ans = new ArrayList<>();
        getPaths(dp, candidates, candidates.length, target, ans, new ArrayList<>());
        return ans;
    }

    // 参考Knapsack的getPaths方法和LongestCommonSubsequence的getAllLCS方法
    void getPaths(int[][] dp, int[] candidates, int i, int j, List<List<Integer>> ans, List<Integer> tmp) {
        // 边界判断
        if (j == 0) {
            ans.add(new ArrayList<>(tmp));
            return;
        }
        if (i == 0) {
            return;
        }
        getPaths(dp, candidates, i - 1, j, ans, tmp);
        // 也可以只判断 j >= candidates[i - 1]，因为dp公式中没有max，min等操作，如果满足条件一定会更新dp[i][j]
        // 故此时一定有dp[i][j] == dp[i][j - candidates[i - 1]] + dp[i - 1][j]
        if (j >= candidates[i - 1] && dp[i][j] == dp[i][j - candidates[i - 1]] + dp[i - 1][j]) {
            tmp.add(candidates[i - 1]);
            getPaths(dp, candidates, i, j - candidates[i - 1], ans, tmp);
            tmp.remove(tmp.size() - 1);
        }
    }
}
