package normal;

import java.util.*;

/**
 * @ClassName: ShortestPathWithAlternatingColors
 * @Description: 1129. 颜色交替的最短路径
 * @Author: zhaooo
 * @Date: 2024/03/23 23:17
 */
public class ShortestPathWithAlternatingColors {
    /**
     * bfs
     * @param n
     * @param redEdges
     * @param blueEdges
     * @return
     */
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<Integer>[] red = new List[n];
        List<Integer>[] blue = new List[n];
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            red[i] = new ArrayList<>();
            blue[i] = new ArrayList<>();
        }
        for (int[] redEdge : redEdges) {
            red[redEdge[0]].add(redEdge[1]);
        }
        for (int[] blueEdge : blueEdges) {
            blue[blueEdge[0]].add(blueEdge[1]);
        }
        Deque<int[]> redDeque = new ArrayDeque<>();
        Deque<int[]> blueDeque = new ArrayDeque<>();
        // 记录以当前颜色结尾的最短路径
        int[] redDp = new int[n];
        int[] blueDp = new int[n];
        for (Integer i : red[0]) {
            redDeque.addLast(new int[]{0, i});
        }
        for (Integer i : blue[0]) {
            blueDeque.addLast(new int[]{0, i});
        }
        int cur = 1;
        while (true) {
            int len1 = redDeque.size();
            int len2 = blueDeque.size();
            if (len1 == 0 && len2 == 0) {
                break;
            }
            for (int i = 0; i < len1; i++) {
                int[] edge = redDeque.removeFirst();
                // 已经访问过 因为是bfs 故当前cur一定大于redDp[edge[1]]
                if (redDp[edge[1]] > 0) {
                    continue;
                }
                // 添加另一种颜色还未访问的节点
                for (Integer j : blue[edge[1]]) {
                    if (blueDp[j] == 0) {
                        blueDeque.addLast(new int[]{edge[1], j});
                    }
                }
                // 记录以当前颜色结尾的最短路径
                redDp[edge[1]] = cur;
            }
            for (int i = 0; i < len2; i++) {
                int[] edge = blueDeque.removeFirst();
                if (blueDp[edge[1]] > 0) {
                    continue;
                }
                for (Integer j : red[edge[1]]) {
                    if (redDp[j] == 0) {
                        redDeque.addLast(new int[]{edge[1], j});
                    }
                }
                blueDp[edge[1]] = cur;
            }
            cur++;
        }
        for (int i = 1; i < n; i++) {
            if (redDp[i] == 0 && blueDp[i] == 0) {
                ans[i] = -1;
            } else if (redDp[i] == 0) {
                ans[i] = blueDp[i];
            } else if (blueDp[i] == 0) {
                ans[i] = redDp[i];
            } else {
                ans[i] = Math.min(redDp[i], blueDp[i]);
            }
        }
        return ans;
    }
}
