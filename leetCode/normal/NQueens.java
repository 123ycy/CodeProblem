package normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: NQueens
 * @Description: 51. N 皇后
 * @Author: zhaooo
 * @Date: 2023/3/18/14:32
 */
public class NQueens {
    int[] map, order, l, r;
    List<List<String>> ret = new ArrayList<>();

    /**
     * dfs+回溯
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        // 标记选择的元素 0未选 1已选
        map = new int[n];
        // order表示每行放置的值，即选择[1,n]的顺序 0表示还未选择
        order = new int[n];
        // l和r分别标记向左和向右倾斜的斜线
        // 同一条斜线上的元素横纵坐标之和相同
        l = new int[n << 1];
        r = new int[n << 1];
        for (int j = 0; j < n; j++) {
            map[j] = 1;
            order[0] = j + 1;
            l[n - 1 - j] = 1;
            r[j] = 1;
            dfs(0, n);
            // 回溯
            map[j] = 0;
            order[0] = 0;
            l[n - 1 - j] = 0;
            r[j] = 0;
        }
        return ret;
    }

    /**
     * 每行选个一个元素递归
     * @param pre
     * @param n
     */
    void dfs(int pre, int n) {
        if (pre == n - 1) {
            ret.add(toStrList(order, n));
            return;
        }
        for (int i = 0; i < n; i++) {
            // 判断是否选择和是否斜线有queen
            if (map[i] == 1 || l[n - i + pre] == 1 || r[i + pre + 1] == 1) {
                continue;
            }
            map[i] = 1;
            order[pre + 1] = i + 1;
            l[n - i + pre] = 1;
            r[i + pre + 1] = 1;
            dfs(pre + 1, n);
            map[i] = 0;
            order[pre + 1] = 0;
            l[n - i + pre] = 0;
            r[i + pre + 1] = 0;
        }
    }

    List<String> toStrList(int[] order, int n) {
        List<String> tmp = new ArrayList<>(n);
        char[] chars = new char[n];
        Arrays.fill(chars, '.');
        for (int i = 0; i < n; i++) {
            chars[order[i] - 1] = 'Q';
            tmp.add(String.valueOf(chars));
            chars[order[i] - 1] = '.';
        }
        return tmp;
    }
}
