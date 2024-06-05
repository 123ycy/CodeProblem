package normal;

import java.util.*;

/**
 * @ClassName: Combinations
 * @Description: 77. 组合
 * @Author: zhaooo
 * @Date: 2023/08/13 09:03
 */
public class Combinations {
    List<List<Integer>> res;
    int n, k;

    /**
     * dfs
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        res = new ArrayList<>();
        this.n = n;
        this.k = k;
        for (int i = 1; i <= n - k + 1; i++) {
            Integer[] cur = new Integer[k];
            cur[0] = i;
            dfs(i + 1, 1, cur);
        }
        return res;
    }

    // 相当于每个长度时[start, n]选择一个 dfs继续
    void dfs(int start, int len, Integer[] cur) {
        if (len == k) {
            res.add(Arrays.asList(cur));
            return;
        }
        // i <= n - k + 1 + idx：剪枝
        for (int i = start; i <= n + len - k + 1; i++) {
            Integer[] newList = cur.clone();
            newList[len] = i;
            dfs(i + 1, len + 1, newList);
        }
    }


    /**
     * dfs+回溯
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (k <= 0 || n < k) {
            return res;
        }
        // 使用path充当全局temp，不需要解法1的clone()
        Deque<Integer> path = new ArrayDeque<>();
        dfs(n, k, 1, path, res);
        return res;
    }

    private void dfs(int n, int k, int index, Deque<Integer> tmp, List<List<Integer>> res) {
        if (tmp.size() == k) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = index; i <= n + tmp.size() - k + 1; i++) {
            tmp.addLast(i);
            dfs(n, k, i + 1, tmp, res);
            // 回溯
            tmp.removeLast();
        }
    }

    /**
     * 遍历
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine3(int n, int k) {
        List<List<Integer>> ret = new ArrayList<>();
        // 1<<n的每一位表示1到n
        for (int i = 0; i < 1 << n; i++) {
            int tmp = i;
            int count = 0;
            while (tmp != 0) {
                // n-1表示把第一个1之后的所有0变为1,当前1变为0
                tmp = tmp & (tmp - 1);
                count++;
            }
            if (count == k) {
                List<Integer> list = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    if (((1 << j) & i) > 0) {
                        // 范围是[1, n]
                        list.add(j + 1);
                    }
                }
                ret.add(list);
            }
        }
        return ret;
    }
}
