package normal;

import java.util.*;

/**
 * @ClassName: KSimilarStrings
 * @Description: 854. 相似度为 K 的字符串
 * @Author: zhaooo
 * @Date: 2022/9/21/11:47
 */
public class KSimilarStrings {
    int len;
    int ret = Integer.MAX_VALUE;
    String s2;
    HashMap<String, Integer> map;

    /**
     * dfs超时 部分剪枝
     * @param s1
     * @param s2
     * @return
     */
    public int kSimilarity(String s1, String s2) {
        this.s2 = s2;
        map = new HashMap<>();
        return dfs(s1, 0);
    }

    int dfs(String s1, int step) {
        char[] cs = s1.toCharArray();
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < len - 1; i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (s1.charAt(j) != s2.charAt(i) || s1.charAt(j) == s2.charAt(j)) {
                    continue;
                }
                swap(cs, i, j);
                String next = String.valueOf(cs);
                swap(cs, i, j);
                if (map.containsKey(next) && map.get(next) <= step + 1) {
                    continue;
                }
                map.put(next, step + 1);
                if (s1.equals(s2)) {
                    return step;
                }
                ans = Math.min(dfs(next, step + 1), ans);
            }
        }
        return ans;
    }

    /**
     * dfs优化：全局ret，记录start即已经匹配的位置
     * @param s1
     * @param s2
     * @return
     */
    public int kSimilarity2(String s1, String s2) {
        return execute(s1.toCharArray(), s2.toCharArray(), 0, 0);
    }

    public int execute(char[] sc1, char[] sc2, int start, int step) {
        // 如果交换次数已经超过"目前最小交换次数result"，终止递归
        if (step >= ret) {
            return ret;
        }
        // 匹配完成且当前step一定小于ret 直接替换
        if (start == sc1.length - 1) {
            return ret = step;
        }
        for (int i = start; i < sc1.length; i++) {
            // 不匹配的才交换
            if (sc1[i] != sc2[i]) {
                for (int j = i + 1; j < sc2.length; j++) {
                    // 和上面一样保证交换玩至少增加一个相同的且不会减少另一个相同的
                    if (sc2[j] == sc1[i] && sc2[j] != sc1[j]) {
                        // 交换
                        swap(sc2, i, j);
                        execute(sc1, sc2, i + 1, step + 1);
                        // 回溯
                        swap(sc2, i, j);
                        // 如果sc1和sc2的i位于j位互为相等，即交换产生第二个相同那么就是最优交换
                        if (sc2[i] == sc1[j]) {
                            break;
                        }
                    }
                }
                return ret;
            }
        }
        return ret = step;
    }

    /**
     * A*算法
     * 关键是 如何设计f(s)即估价函数且f(s)<=g(s)，g(s)表示s到终点的代价
     * 优先队列取添加一个取一个而不是添加一堆取一个
     * @param s1
     * @param s2
     * @return
     */
    public int kSimilarity3(String s1, String s2) {
        if (s1.equals(s2)) {
            return 0;
        }
        len = s1.length();
        this.s2 = s2;
        // 记录s1到每个S的step
        Map<String, Integer> map = new HashMap<>();
        // 考虑当前 状态到起点的代价 和 当前状态到终点的预计代价 的优先队列
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
            int sa = map.get(a), sb = map.get(b), fa = f(a), db = f(b);
            return sa + fa - sb - db;
        });
        map.put(s1, 0);
        pq.add(s1);
        while (!pq.isEmpty()) {
            String cur = pq.poll();
            char[] cs = cur.toCharArray();
            int step = map.get(cur);
            int idx = 0;
            // 找到第一个不同的字符开始交换
            while (idx < len && cs[idx] == s2.charAt(idx)) {
                idx++;
            }
            // 将cur的idx和i交换
            for (int i = idx + 1; i < len; i++) {
                // 不能增加相似的交换则掠过
                if (cs[i] != s2.charAt(idx) || cs[i] == s2.charAt(i)) {
                    continue;
                }
                // 交换
                swap(cs, idx, i);
                String next = String.valueOf(cs);
                if (next.equals(s2)) {
                    return step + 1;
                }
                // 回溯
                swap(cs, idx, i);
                if (map.containsKey(next) && map.get(next) <= step + 1) {
                    continue;
                }
                map.put(next, step + 1);
                pq.add(next);
            }
        }
        // 题目保证有解不可达
        return -1;
    }

    // f(s)是估价函数，并且估价函数要满足 f(s)<=g(s)，其中 g(s)表示s到终点的代价
    // 由于每一次交换最多可减少两个不同的字符，计算a与b的不同字符数量 ans，对应的理论最小转换次数为 (ans+1)/2 （向下取整）
    // 即对于所有点启发函数的估计值小于实际值

    // 直观理解：A*算法每次按照估价函数最小的原则来选取节点。假设估价函数估计的代价等于实际代价，那么我直接就选取到最优路径上的各个节点了；
    // 而A*算法中估价函数估计的代价总是比实际代价更小一点，并且随着选取节点数量的增多，估价函数估计的代价越来越接近于实际代价，从而不存在选取了更长路径的情况。
    // 因为如果存在更短的路径，那么它的估计代价应该更小即对应f(s)更小（f(s)<=g(s)使f(s)有上限，g(s)变小f(s)再趋势上变小），因此优先被选取
    int f(String s1) {
        int ret = 0;
        for (int i = 0; i < len; i++) {
            ret += s1.charAt(i) == s2.charAt(i) ? 0 : 1;
        }
        // 小于等于g(s)的最小值
        return ret + 1 >> 1;
    }

    void swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }
}
