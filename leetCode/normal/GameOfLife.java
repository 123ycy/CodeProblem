package normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: GameOfLife
 * @Description: 289. 生命游戏
 * @Author: zhaooo
 * @Date: 2023/7/14/9:37
 */
public class GameOfLife {
    /**
     * 用位图保存每一排
     *
     * @param board
     */
    public void gameOfLife(int[][] board) {
        int m = board.length, n = board[0].length;
        // m+2保证不越界
        int[] set = new int[m + 2];
        // 每个位图对应1的数量
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // 010
            map.put(2 << i, 1);
            // 101
            map.put(5 << i, 2);
            // 110
            map.put(3 << i, 2);
            // 111
            map.put(7 << i, 3);
        }
        map.put(0, 0);
        for (int i = 0; i < m; i++) {
            int cur = 0;
            for (int j = 0; j < n; j++) {
                // board[i][j] << j + 1 保证最左侧是0 不越界
                cur |= board[i][j] << j + 1;
            }
            set[i + 1] = cur;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // (111) <<< j保证中间的1是对应board[i][j]在set中的位置，因为set的最左侧是0
                int count = 0, mask = 7 << j;
                // 当前点上面
                count += map.get(set[i] & mask);
                // 当前点下面
                count += map.get(set[i + 2] & mask);
                // 当前点左侧
                if ((set[i + 1] & 1 << j) > 0) count++;
                // 当前点右侧
                if ((set[i + 1] & 1 << j + 2) > 0) count++;
                if (count < 2 || count > 3) board[i][j] = 0;
                if (count == 3) board[i][j] = 1;
            }
        }
    }

    /**
     * 原有的最低位存储的是当前状态，那倒数第二低位存储下一个状态就行了。
     *
     * @param board
     */
    public void gameOfLife2(int[][] board) {
        if (board.length == 0) {
            return;
        }
        int n = board.length;
        int m = board[0].length;
        int[] DX = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] DY = {1, -1, 0, 0, 1, -1, 1, -1};
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                int cnt = 0;
                for (int k = 0; k < 8; k++) {
                    int x = i + DX[k];
                    int y = j + DY[k];
                    if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
                        continue;
                    }
                    // 这里不能直接加board[x][y]，因为 board[x][y] 的倒数第二位是可能有值的。
                    cnt += board[x][y] & 1;
                }
                if ((board[i][j] & 1) > 0) {
                    // 这个是活细胞
                    if (cnt >= 2 && cnt <= 3) {
                        // 周围有2/3个活细胞，下一个状态还是活细胞。
                        board[i][j] = 0b11;
                    }
                    // 周围活细胞过多或过少都会死，因为原数据是0b01，所以这里不用额外赋值。
                } else if (cnt == 3) {
                    // 这个是死细胞，周围有3个活细胞，下一个状态变为活细胞。
                    board[i][j] = 0b10;
                }
            }
        }
        // 最后一位去掉，倒数第二位变为更新后的状态。
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                board[i][j] >>= 1;
            }
        }
    }
}
