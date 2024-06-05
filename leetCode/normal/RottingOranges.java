package normal;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @ClassName: RottingOranges
 * @Description: 994. 腐烂的橘子
 * @Author: zhaooo
 * @Date: 2023/3/21/10:12
 */
public class RottingOranges {
    /**
     * 使用数组作为队列
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int m = grid.length;
        int n = grid[0].length;
        // queue[100]时太小，就算用循环队列也不行
        int[] queue = new int[1000];
        int head = 0, tail = 0, oldTail = 0, oranges = 0, count = 0, ret = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue[tail++] = i * 10 + j;
                    // 更新grid[i][j]为1，因为位置已经加入队列，肯定会被标记为感染
                    grid[i][j] = 1;
                }
                if (grid[i][j] == 1) {
                    oranges++;
                }
            }
        }
        while (head != tail) {
            // head == oldTail时表示概论队列内所有元素已经访问
            if (head == oldTail) {
                // 每轮结束更新ret，开启下一轮
                ret++;
                oldTail = tail;
            }
            int cur = queue[head++];
            int x = cur / 10;
            int y = cur % 10;
            // grid[x][y] = 1为感染，grid[x][y] = 2,3,... 表示已经感染，此时grid[x][y]的值-2表示第几轮被感染
            // grid[x][y] == 0表示没橘子
            if (grid[x][y] > 1 || grid[x][y] == 0) {
                continue;
            }
            // 每次取出cur就更新grid对应的cur，标记访问
            grid[x][y] = ret;
            // count表示已经感染的橘子数量
            count++;
            for (int[] dir : dirs) {
                int x_ = x + dir[0];
                int y_ = y + dir[1];
                if (x_ < 0 || x_ >= m || y_ < 0 || y_ >= n) {
                    continue;
                }
                queue[tail++] = x_ * 10 + y_;
            }
        }
        // Math.max(0, ret - 3)最后一次ret会再++
        return count == oranges ? Math.max(0, ret - 3) : -1;
    }

    /**
     * 使用ArrayDeque
     * @param grid
     * @return
     */
    public int orangesRotting2(int[][] grid) {
        //定义4个方向的位移
        int[] dx = new int[]{1, 0, 0, -1};
        int[] dy = new int[]{0, 1, -1, 0};
        //创建时间变量来计时
        int time = 0;
        //新鲜橘子计数,用于最后判断没被感染的橘子
        int cnt = 0;
        //先将烂橘子压入队列
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //烂橘子加入队列
                if (grid[i][j] == 2) {
                    queue.add(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    ++cnt;
                }
            }
        }
        //如果新鲜橘子为0,返回0
        if (cnt == 0) {
            return 0;
        }
        //摆烂时刻！
        while (!queue.isEmpty()) {
            //每次从当前的所有烂橘子向其四周开始腐烂一次
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cell = queue.remove();
                int x = cell[0], y = cell[1];
                for (int j = 0; j < 4; j++) {
                    int mx = x + dx[j];
                    int my = y + dy[j];
                    if (mx >= 0 && my >= 0 && mx < m && my < n && grid[mx][my] == 1) {
                        //新鲜橘子腐烂
                        grid[mx][my] = 2;
                        //将本次队列腐烂的所有橘子压入队列
                        queue.offer(new int[]{mx, my});
                        //新鲜橘子减少
                        --cnt;
                    }
                }
            }
            //BFS的每一层作为一次时间增加
            ++time;
        }
        //因为队列中最后一层橘子遍历后时间会加1,但没有橘子被继续感染,此处多了一次1
        return cnt == 0 ? time - 1 : -1;
    }
}