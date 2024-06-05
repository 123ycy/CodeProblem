package normal;

import java.util.PriorityQueue;

/**
 * @ClassName: MinimumNumberOfRefuelingStops
 * @Description: 871. 最低加油次数
 * @Author: zhaooo
 * @Date: 2022/7/2/18:20
 */
public class MinimumNumberOfRefuelingStops {
    /**
     * 暴力dfs
     * 超时
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        if (stations.length == 0) {
            return target > startFuel ? -1 : 0;
        }
        int res = dfs(target, -1, startFuel, 0, stations);
        return res < 1e9 + 10 ? res : -1;
    }

    public int dfs(int target, int cur, int startFuel, int times, int[][] stations) {
        if (cur != -1 && startFuel + stations[cur][0] >= target) {
            return times;
        }
        if (cur == stations.length - 1) {
            return Integer.MAX_VALUE;
        }
        startFuel -= (stations[cur + 1][0] - (cur == -1 ? 0 : stations[cur][0]));
        if (startFuel < 0) {
            return Integer.MAX_VALUE;
        }
        // 要不要再下一站停车加油
        return Math.min(dfs(target, cur + 1, startFuel, times, stations), dfs(target, cur + 1, startFuel + stations[cur + 1][1], times + 1, stations));
    }

    /**
     * 动态规划
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops2(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        // dp[i] 表示加油i次能行驶的距离
        long[] dp = new long[n + 1];
        dp[0] = startFuel;
        for (int i = 0; i < n; i++) {
            if (dp[i] < stations[i][0]) {
                return -1;
            }
            for (int j = i; j >= 0; j--) {
                // 当前加油次数能到该站
                if (dp[j] >= stations[i][0]) {
                    dp[j + 1] = Math.max(dp[j + 1], dp[j] + stations[i][1]);
                } else {
                    // 不能到站，比他加油次数少的也不能到站
                    break;
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            if (dp[i] >= target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 贪心 优先队列
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops3(int target, int startFuel, int[][] stations) {
        //最大堆
        PriorityQueue<Integer> heap = new PriorityQueue<>(((o1, o2) -> o2 - o1));
        int ans = 0;
        int curGas = startFuel;
        int i = 0;
        while (curGas < target) {
            //只要当前油量可以支撑到下一个加油站，就选择不加油
            if (i < stations.length && curGas >= stations[i][0]) {
                heap.add(stations[i][1]);
                i++;
            } else {
                //无法到达，加油
                if (!heap.isEmpty()) {
                    //堆中有元素 证明路过很多加油站，选择最多油的加油站加油
                    curGas += heap.poll();
                    ans++;
                } else {
                    //无元素，证明无法到达
                    return -1;
                }
            }
        }
        return ans;
    }
}
