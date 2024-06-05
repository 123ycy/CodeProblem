package normal;

import java.util.Arrays;

/**
 * @ClassName: MatchsticksToSquare
 * @Description: 473. 火柴拼正方形
 * @Author: zhaooo
 * @Date: 2022/6/3/12:44
 */
public class MatchsticksToSquare {
    int len, sum, edge, used;

    /**
     * 超时
     * @param matchsticks
     * @return
     */
    public boolean makesquare(int[] matchsticks) {
        used = 0;
        sum = Arrays.stream(matchsticks).sum();
        len = matchsticks.length;
        if (sum % 4 != 0) {
            return false;
        }
        edge = sum >> 2;
        Arrays.sort(matchsticks);
        if (matchsticks[len - 1] > sum) {
            return false;
        }
        return dfs(matchsticks, 0, 0, 0);
    }

    public boolean dfs(int[] matchsticks, int curUsed, int curSum, int count) {
        if (count == 4) {
            return true;
        }
        if (curSum == edge) {
            used += curUsed;
            if (dfs(matchsticks, 0, 0, count + 1)) {
                return true;
            }
            // 回溯
            used -= curUsed;
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (((used | curUsed) & (1 << i)) > 0) {
                continue;
            }
            int tmpSum = curSum + matchsticks[i];
            // 剪枝
            if (tmpSum > edge) {
                return false;
            }
            if (dfs(matchsticks, curUsed | (1 << i), tmpSum, count)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 放置4条边(桶)
     * @param matchsticks
     * @return
     */
    public boolean makesquare2(int[] matchsticks) {
        int totalLen = Arrays.stream(matchsticks).sum();
        if (totalLen % 4 != 0) {
            return false;
        }
        Arrays.sort(matchsticks);
        // 倒序,comparator不能对基本数据类型排序
        for (int i = 0, j = matchsticks.length - 1; i < j; i++, j--) {
            int temp = matchsticks[i];
            matchsticks[i] = matchsticks[j];
            matchsticks[j] = temp;
        }
        int[] edges = new int[4];
        return dfs(0, matchsticks, edges, totalLen / 4);
    }

    public boolean dfs(int index, int[] matchsticks, int[] edges, int len) {
        // 使用完全部火柴
        if (index == matchsticks.length) {
            return true;
        }
        for (int i = 0; i < edges.length; i++) {
            // index为火柴下标
            // 如果加入当前火柴后长度大于len或者当前桶长度等于前一个桶长度时跳过
            if (edges[i] + matchsticks[index] > len || ((i > 0 && edges[i] == edges[i - 1]))) {
                continue;
            }
            edges[i] += matchsticks[index];
            if (dfs(index + 1, matchsticks, edges, len)) {
                return true;
            }
            // 回溯
            edges[i] -= matchsticks[index];
        }
        return false;
    }

    /**
     * 状态压缩+动态规划
     * @param matchsticks
     * @return
     */
    public boolean makesquare3(int[] matchsticks) {
        int totalLen = Arrays.stream(matchsticks).sum();
        if (totalLen % 4 != 0) {
            return false;
        }
        int len = totalLen / 4, n = matchsticks.length;
        //用状态s记录哪些火柴已经被放入（s 的第 k 位为 1 表示第 k 根火柴已经被放入） dp[s] 表示正方形未放满的边的当前长度
        int[] dp = new int[1 << n];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int s = 1; s < (1 << n); s++) {
            for (int k = 0; k < n; k++) {
                // 没有使用第k根火柴
                if ((s & (1 << k)) == 0) {
                    continue;
                }
                // 去掉第k根火柴(当前已经使用该根火柴)
                int s1 = s & ~(1 << k);
                if (dp[s1] != -1 && dp[s1] + matchsticks[k] <= len) {
                    // 更新dp[s] 表示该火柴可以放入(dp[s]=-1时表示不合法)
                    // %len 表示放入下一条边
                    dp[s] = (dp[s1] + matchsticks[k]) % len;
                    break;
                }
            }
        }
        // 是否刚好放满
        return dp[(1 << n) - 1] == 0;
    }
}
