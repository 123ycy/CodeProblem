import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Knapsack
 * @Description: 背包问题
 * https://www.geeksforgeeks.org/introduction-to-knapsack-problem-its-types-and-how-to-solve-them/
 * @Author: zhaooo
 * @Date: 2024/05/01 12:37
 */
public class Knapsack {
    /**
     * 01背包
     */
    // 二维dp
    public int knapsack(int[] weight, int[] value, int W) {
        int n = weight.length;
        // dp[i][w]表示前i个物品，背包容量为w时的最大价值
        int[][] dp = new int[n + 1][W + 1];
        // 物品和背包容量的遍历顺序可以互换

        // 先遍历物品，再遍历背包容量
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                // 当前背包容量装不下，只能选择不装入背包
                if (w - weight[i - 1] < 0) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weight[i - 1]] + value[i - 1]);
                }
            }
        }
        // 先遍历背包容量，再遍历物品 二者都可以
        // for (int w = 1; w <= W; w++) {
        //     for (int i = 1; i <= n; i++) {
        //         if (w - weight[i - 1] < 0) {
        //             dp[i][w] = dp[i - 1][w];
        //         } else {
        //             dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weight[i - 1]] + value[i - 1]);
        //         }
        //     }
        // }
        return dp[n][W];
    }

    // 1维dp 必须先遍历物品，再遍历背包容量 且若是01背包问题，必须逆序遍历物品
    public int knapsack2(int[] weight, int[] value, int W) {
        int n = weight.length;
        // dp[j]表示背包容量为j时的最大价值，dp[j]其实是是dp[x][j]（二维dp中的对应列），即二维dp中的对应行的信息。故需要每轮遍历行（容量），即先遍历物品
        // 如果先遍历背包容量，再遍历物品，当逆序遍历物品时，那么每个dp[j]就只会放入一个能放入的价值最高的物品，即：背包里只放入了一个物品。
        // 如果先遍历背包容量，再遍历物品，当正序遍历物品时，会出现重复选择问题。
        int[] dp = new int[W + 1];
        // 必须先遍历物品，再遍历背包容量
        for (int i = 1; i <= n; i++) {
            // 为了消除重复选择的问题，逆序遍历
            for (int w = W; w >= 1; w--) {
                if (w - weight[i - 1] >= 0) {
                    dp[w] = Math.max(dp[w], dp[w - weight[i - 1]] + value[i - 1]);
                }
            }
        }
        return dp[W];
    }


    /**
     * 完全背包
     */
    // 二维dp
    public int completeKnapsack(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[][] dp = new int[n + 1][W + 1];
        // 先遍历物品，再遍历背包容量
        // 也可以先遍历背包容量，再遍历物品
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (w - weight[i - 1] < 0) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    // 完全背包问题和0/1背包问题的区别就在于这里：是否可以使用dp[i]（当前列）来更新dp[i]
                    // 0/1背包问题是dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weight[i - 1]] + value[i - 1]);
                    // 而完全背包问题是dp[i][w] = Math.max(dp[i - 1][w], dp[i][w - weight[i - 1]] + value[i - 1]);
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i][w - weight[i - 1]] + value[i - 1]);
                }
            }
        }
        return dp[n][W];
    }

    // 1维dp
    public int completeKnapsack2(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[] dp = new int[W + 1];
        // 先遍历物品，再遍历背包容量
        // 也可以先遍历背包容量，再遍历物品
        for (int i = 1; i <= n; i++) {
            // 保证可以重复选择，正序遍历
            for (int w = 1; w <= W; w++) {
                if (w - weight[i - 1] >= 0) {
                    dp[w] = Math.max(dp[w], dp[w - weight[i - 1]] + value[i - 1]);
                }
            }
        }
        return dp[W];
    }


    /**
     * 通过dp公式和二维dp数组倒推出路径
     */
    // longestCommonSubsequence步长为1，而背包问题步长为weight[i]
    void getPaths(int[][] dp, int[] weight, int[] value, int i, int j, List<List<Integer>> ans, List<Integer> list) {
        if (i == 0 || j == 0) {
            ans.add(new ArrayList<>(list));
            return;
        }
        if (dp[i][j] == dp[i - 1][j]) {
            getPaths(dp, weight, value, i - 1, j, ans, list);
        }
        // 如果是完全背包问题，这里是dp[i][j] == dp[i][j - weight[i - 1]] + value[i - 1]
        if (j >= weight[i - 1] && dp[i][j] == dp[i - 1][j - weight[i - 1]] + value[i - 1]) {
            list.add(i - 1);
            getPaths(dp, weight, value, i, j - weight[i - 1], ans, list);
            list.remove(list.size() - 1);
        }
    }
}
