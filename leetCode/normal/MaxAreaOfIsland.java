package normal;

/**
 * @ClassName: MaxAreaOfIsland
 * @Description: 695. 岛屿的最大面积
 * @Author: zhaooo
 * @Date: 2022/9/19/8:55
 */
public class MaxAreaOfIsland {
    int[] father, size;
    final int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int ret = 0;

    /**
     * 并查集
     * @param grid
     * @return
     */
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        father = new int[m * n];
        size = new int[m * n];
        // 初始化并查集
        for (int i = 0; i < m * n; i++) {
            father[i] = i;
            size[i] = 1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                // ret的初始值为0
                ret = Math.max(1, ret);
                // 当grid[i][j] == 1时遍历不同方向并合并
                for (int[] dir : dirs) {
                    int x = i + dir[0], y = j + dir[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 1) {
                        union(i * n + j, x * n + y);
                    }
                }
            }
        }
        return ret;
    }

    // 查找并路径压缩
    int find(int x) {
        return father[x] == x ? x : (father[x] = find(father[x]));
    }

    // 按大小合并并修改结果
    void union(int a, int b) {
        int fa = find(a), fb = find(b);
        if (fa == fb) {
            return;
        }
        if (size[fa] < size[fb]) {
            size[fb] += size[fa];
            father[fa] = fb;
            ret = Math.max(size[fb], ret);
        } else {
            size[fa] += size[fb];
            father[fb] = fa;
            ret = Math.max(size[fa], ret);
        }
    }

    /**
     * dfs + 沉岛思想
     * @param grid
     * @return
     */
    public int maxAreaOfIsland2(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    res = Math.max(res, dfs(i, j, grid));
                }
            }
        }
        return res;
    }

    // 是陆地返回1 并将该陆地变为海洋即grid[i][j]=0 在下次dfs时不会访问该格 相当于访问标记
    // 其他情况返回0
    private int dfs(int i, int j, int[][] grid) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length || grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        int num = 1;
        // 不用for循环 直接遍历 效率更高
        for (int[] dir : dirs) {
            num += dfs(i + dir[0], j + dir[1], grid);
        }
        return num;
    }
}
