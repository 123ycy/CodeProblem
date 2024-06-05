package normal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SnakesAndLadders
 * @Description: 909. 蛇梯棋
 * @Author: zhaooo
 * @Date: 2023/08/01 12:50
 */
public class SnakesAndLadders {
    int n;
    int[] nums;

    /**
     * bfs
     * 求的是最短路径长度，用bfs结果一定是最短路径。dfs出现的结果不能保证是最短路径。
     *
     * @param board
     * @return
     */
    public int snakesAndLadders(int[][] board) {
        n = board.length;
        if (board[0][0] != -1) return -1;
        nums = new int[n * n + 1];
        // 是否正序
        boolean isRight = true;
        // 扁平化board
        for (int i = n - 1, idx = 1; i >= 0; i--) {
            for (int j = (isRight ? 0 : n - 1); isRight ? j < n : j >= 0; j += isRight ? 1 : -1) {
                nums[idx++] = board[i][j];
            }
            isRight = !isRight;
        }
        return bfs();
    }

    int bfs() {
        // bfs队列
        Deque<Integer> d = new ArrayDeque<>();
        // 保存下标到起点距离
        Map<Integer, Integer> m = new HashMap<>();
        d.addLast(1);
        m.put(1, 0);
        while (!d.isEmpty()) {
            int poll = d.pollFirst();
            int step = m.get(poll);
            // 到达终点
            if (poll == n * n) return step;
            for (int i = 1; i <= 6; i++) {
                int np = poll + i;
                if (np > n * n) continue;
                // 遇到要跳转的情况，只跳转一次
                if (nums[np] != -1) np = nums[np];
                // 已经访问过的，step+1大于m.get(np)
                // bfs是最短路径算法，下一层路径更长
                if (m.containsKey(np)) continue;
                m.put(np, step + 1);
                d.addLast(np);
            }
        }
        return -1;
    }
}
