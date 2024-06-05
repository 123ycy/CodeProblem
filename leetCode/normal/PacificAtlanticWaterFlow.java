package normal;

import java.util.*;

/**
 * @ClassName: PacificAtlanticWaterFlow
 * @Description: 417. 太平洋大西洋水流问题
 * @Author: zhaooo
 * @Date: 2022/9/24/17:56
 */
public class PacificAtlanticWaterFlow {
    int m, n;
    int[][] hg;
    int[][] dirs = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    List<List<Integer>> ret = new ArrayList<>();

    /**
     * 分别2次从边界到另外边界的dfs
     * 可优化为用另外一个二维数组标识访问1为访问1次2为访问2次
     * @param heights
     * @return
     */
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        Set<Integer> pSet = new HashSet<>(), aSet = new HashSet<>();
        List<int[]> pDq = new ArrayList<>(), aDq = new ArrayList<>();
        m = heights.length;
        n = heights[0].length;
        hg = heights;
        // 横着
        for (int i = 0; i < n; i++) {
            pDq.add(new int[]{0, i});
            aDq.add(new int[]{m - 1, i});
        }
        // 竖着
        for (int i = 0; i < m; i++) {
            pDq.add(new int[]{i, 0});
            aDq.add(new int[]{i, n - 1});
        }
        for (int[] cur : pDq) {
            dfs(cur[0], cur[1], pSet, aSet);
        }
        for (int[] cur : aDq) {
            dfs(cur[0], cur[1], aSet, pSet);
        }
        return ret;
    }

    int f(int i, int j) {
        return i * 1000 + j;
    }

    void dfs(int i, int j, Set<Integer> curSet, Set<Integer> otherSet) {
        int cur = f(i, j);
        if (curSet.contains(cur)) {
            return;
        }
        curSet.add(cur);
        if (otherSet.contains(cur)) {
            List<Integer> list = new ArrayList<>(2);
            list.add(i);
            list.add(j);
            ret.add(list);
        }
        for (int[] dir : dirs) {
            int i_ = i + dir[0], j_ = j + dir[1];
            if (i_ < 0 || i_ == m || j_ < 0 || j_ == n || hg[i][j] > hg[i_][j_]) {
                continue;
            }
            dfs(i_, j_, curSet, otherSet);
        }
    }
}
