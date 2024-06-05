package normal;

/**
 * @ClassName: NQueensII
 * @Description: 52. N 皇后 II
 * @Author: zhaooo
 * @Date: 2023/8/14/9:18
 */
public class NQueensII {
    int ret = 0;
    int n;
    int[] h, l, r;

    /**
     * dfs+回溯
     *
     * @param n
     * @return
     */
    public int totalNQueens(int n) {
        this.n = n;
        // h表示水平，l表示向左倾斜，r表示向右倾斜
        h = new int[n];
        l = new int[n << 1];
        r = new int[n << 1];
        dfs(0);
        return ret;
    }

    void dfs(int idx) {
        // idx是当前的行数
        if (idx == n) {
            ret++;
            return;
        }
        for (int i = 0; i < n; i++) {
            // 考虑i=0,idx=0等的极限情况即可推出idx和l,r的关系
            if (h[i] == 1 || l[i - idx + n] == 1 || r[i + idx] == 1) {
                continue;
            }
            h[i] = 1;
            l[i - idx + n] = 1;
            r[i + idx] = 1;
            // 递归
            dfs(idx + 1);
            // 回溯
            h[i] = 0;
            l[i - idx + n] = 0;
            r[i + idx] = 0;
        }
    }
}
