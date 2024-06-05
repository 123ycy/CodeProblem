package normal;

import java.util.*;

/**
 * @ClassName: MakingALargeIsland
 * @Description: 827. 最大人工岛
 * @Author: zhaooo
 * @Date: 2022/9/18/14:04
 */
public class MakingALargeIsland {
    final int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    // 改变一格可以联通的块中的点
    List<List<Integer>> links = new ArrayList<>();
    // 联通块大小，对应下标为块号
    List<Integer> size = new ArrayList<>();
    int n;
    int[] father, sz;

    /**
     * dfs
     * 首先跑一次dfs找到所有的联通块，统计每个点所属的联通块标号以及该联通块下点的个数。遍历过程中添加为0的点的四周为1的点，最后进行合并判断。
     * @param grid
     * @return
     */
    public int largestIsland(int[][] grid) {
        n = grid.length;
        int ret = 0;
        // 联通块号不能为0和1
        size.add(0);
        size.add(0);
        // 0为空1为陆地 联通块从2开始
        int num = 1;
        // 查找联通块
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 找到一个未标号的联通集先在size中初始化
                    size.add(0);
                    dfs(grid, i, j, ++num);
                }
                ret = Math.max(ret, size.get(num));
            }
        }
        // 特殊情况直接退出
        if (ret == n * n) {
            return ret;
        }
        if (ret == 0) {
            return 1;
        }
        // 去除不同连接
        for (List<Integer> link : links) {
            // 使用set因为可能出现相同的联通块号
            Set<Integer> tmpSet = new HashSet<>();
            // 初始值为1即要更改的格子
            int tmpSum = 1;
            for (int idx = 0; idx < link.size(); ) {
                int i = link.get(idx++);
                int j = link.get(idx++);
                tmpSet.add(grid[i][j]);
            }
            for (Integer tmpNum : tmpSet) {
                tmpSum += size.get(tmpNum);
            }
            ret = Math.max(ret, tmpSum);
        }
        return ret;
    }

    void dfs(int[][] grid, int i, int j, int num) {
        if (i < 0 || i == n || j < 0 || j == n) {
            return;
        }
        // 未标号的陆地
        if (grid[i][j] == 1) {
            // 加入当前联通块并更改大小
            grid[i][j] = num;
            size.set(num, size.get(num) + 1);
            // 递归查找
            for (int[] d : dirs) {
                dfs(grid, i + d[0], j + d[1], num);
            }
            // 经过一步到达的海域
        } else if (grid[i][j] == 0) {
            List<Integer> curLink = new ArrayList<>();
            for (int[] d : dirs) {
                // 查找跟此海域相连的岛屿即联通块，块号也有可能为num即该连通块
                if (i + d[0] >= 0 && i + d[0] < n && j + d[1] >= 0 && j + d[1] < n
                        && grid[i + d[0]][j + d[1]] > 0) {
                    // 添加坐标而不是块号因为可能该联通块还未访问即没标号
                    curLink.add(i + d[0]);
                    curLink.add(j + d[1]);
                }
            }
            links.add(curLink);
        }
    }

    /**
     * 并查集
     * https://leetcode.cn/problems/making-a-large-island/solution/by-ac_oier-1kmp/
     * @param grid
     * @return
     */

    public int largestIsland2(int[][] grid) {
        int n = grid.length;
        father = new int[n * n + 10];
        // 大小数组 一个数组包含秩和大小两个功能
        sz = new int[n * n + 10];
        // 初始化并查集
        for (int i = 1; i <= n * n; i++) {
            father[i] = i;
            sz[i] = 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                // grid[i][j] != 0
                for (int[] d : dirs) {
                    int x = i + d[0], y = j + d[1];
                    if (x < 0 || x >= n || y < 0 || y >= n || grid[x][y] == 0) {
                        continue;
                    }
                    // 将grid[i][j]和不同方向合并
                    union(i * n + j + 1, x * n + y + 1);
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 陆地则计算大小
                if (grid[i][j] == 1) {
                    ans = Math.max(ans, sz[find(i * n + j + 1)]);
                } else {
                    int tot = 1;
                    Set<Integer> set = new HashSet<>();
                    // 遍历不同方向
                    for (int[] di : dirs) {
                        int x = i + di[0], y = j + di[1];
                        if (x < 0 || x >= n || y < 0 || y >= n || grid[x][y] == 0) {
                            continue;
                        }
                        int father = find(x * n + y + 1);
                        if (set.contains(father)) {
                            continue;
                        }
                        // 可以添加的陆地则加入
                        tot += sz[father];
                        set.add(father);
                    }
                    ans = Math.max(ans, tot);
                }
            }
        }
        return ans;
    }

    int find(int x) {
        // 路径压缩
        return father[x] == x ? x : (father[x] = find(father[x]));
    }

    void union(int a, int b) {
        int fa = find(a), fb = find(b);
        if (fa == fb) {
            return;
        }
        // 按秩（大小）合并修改大小
        if (sz[fa] <= sz[fb]) {
            // a的大小小于b则a并入b
            father[fa] = fb;
            sz[fb] += sz[fa];
        } else {
            union(b, a);
        }
    }
}
