package normal;

import java.util.*;

/**
 * @ClassName: WordLadder
 * @Description: 127. 单词接龙
 * @Author: zhaooo
 * @Date: 2023/08/03 13:15
 */
public class WordLadder {
    String s, e;
    Set<String> set = new HashSet<>();

    /**
     * 双向bfs
     * 求的是最短路径长度，用bfs结果一定是最短路径。dfs出现的结果不能保证是最短路径。
     *
     * @param _s
     * @param _e
     * @param ws
     * @return
     */
    public int ladderLength(String _s, String _e, List<String> ws) {
        s = _s;
        e = _e;
        // 将所有 word 存入 set，如果目标单词不在 set 中，说明无解
        set.addAll(ws);
        if (!set.contains(e)) return 0;
        int ans = bfs();
        return ans == -1 ? 0 : ans + 1;
    }

    int bfs() {
        // d1 代表从起点 beginWord 开始搜索（正向）
        // d2 代表从结尾 endWord 开始搜索（反向）
        Deque<String> d1 = new ArrayDeque<>(), d2 = new ArrayDeque<>();
        /*
         * m1 和 m2 分别记录两个方向出现的单词是经过多少次转换而来
         * e.g.
         * m1 = {"abc":1} 代表 abc 由 beginWord 替换 1 次字符而来
         * m2 = {"xyz":3} 代表 xyz 由 endWord 替换 3 次字符而来
         */
        Map<String, Integer> m1 = new HashMap<>(), m2 = new HashMap<>();
        d1.add(s);
        m1.put(s, 0);
        d2.add(e);
        m2.put(e, 0);
        /*
         * 只有两个队列都不空，才有必要继续往下搜索
         * 如果其中一个队列空了，说明从某个方向搜到底都搜不到该方向的目标节点
         * e.g.
         * 例如，如果 d1 为空了，说明从 beginWord 搜索到底都搜索不到 endWord，反向搜索也没必要进行了
         */
        while (!d1.isEmpty() && !d2.isEmpty()) {
            int t = -1;
            // 为了让两个方向的搜索尽可能平均，优先拓展队列内元素少的方向
            if (d1.size() <= d2.size()) {
                t = update(d1, m1, m2);
            } else {
                t = update(d2, m2, m1);
            }
            if (t != -1) return t;
        }
        return -1;
    }

    // update 代表从 deque 中取出一个单词进行扩展，
    // cur 为当前方向的距离字典；other 为另外一个方向的距离字典
    int update(Deque<String> deque, Map<String, Integer> cur, Map<String, Integer> other) {
        int m = deque.size();
        while (m-- > 0) {
            // 获取当前需要扩展的原字符串
            String poll = deque.removeFirst();
            int n = poll.length();
            // 枚举替换原字符串的哪个字符 i
            for (int i = 0; i < n; i++) {
                // 枚举将 i 替换成哪个小写字母
                for (int j = 0; j < 26; j++) {
                    // 替换后的字符串
                    String sub = poll.substring(0, i) + (char) ('a' + j) + poll.substring(i + 1);
                    if (set.contains(sub)) {
                        // 如果该字符串在「当前方向」被记录过（拓展过）并且举例更短，跳过即可
                        if (cur.containsKey(sub) && cur.get(sub) <= cur.get(poll) + 1) continue;
                        // 如果该字符串在「另一方向」出现过，说明找到了联通两个方向的最短路
                        if (other.containsKey(sub)) {
                            // start到poll距离+poll到sub距离（1）+sub到end距离
                            return cur.get(poll) + 1 + other.get(sub);
                        } else {
                            // 否则加入 deque 队列
                            deque.addLast(sub);
                            // 更新距离
                            cur.put(sub, cur.get(poll) + 1);
                        }
                    }
                }
            }
        }
        return -1;
    }


    // 在上面已经定义
    // String s, e;
    // Set<String> set = new HashSet<>();
    /**
     * A*算法
     */
    int INF = 0x3f3f3f3f;

    public int ladderLength2(String _s, String _e, List<String> ws) {
        s = _s;
        e = _e;
        set.addAll(ws);
        if (!set.contains(e)) return 0;
        int ans = aStar();
        return ans == -1 ? 0 : ans + 1;
    }

    int aStar() {
        // 小根堆，保存start经过sub到endWord的距离
        PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        // 保存sub到startWord的距离
        Map<String, Integer> dist = new HashMap<>();
        dist.put(s, 0);
        q.add(new Node(s, f(s)));

        while (!q.isEmpty()) {
            // 每次从中取出到endWord距离最小的节点
            Node poll = q.poll();
            String str = poll.str;
            int distance = dist.get(str);
            if (str.equals(e)) {
                break;
            }
            int n = str.length();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 26; j++) {
                    String sub = str.substring(0, i) + (char) ('a' + j) + str.substring(i + 1);
                    if (!set.contains(sub)) continue;
                    if (!dist.containsKey(sub) || dist.get(sub) > distance + 1) {
                        dist.put(sub, distance + 1);
                        // startWord经过sub到endWord的距离，因为输出结果是startWord到endWord的最短距离
                        q.add(new Node(sub, dist.get(sub) + f(sub)));
                    }
                }
            }
        }
        return dist.getOrDefault(e, -1);
    }

    // 找到sub和endWord的距离
    int f(String str) {
        if (str.length() != e.length()) return INF;
        int n = str.length();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += str.charAt(i) == e.charAt(i) ? 0 : 1;
        }
        return ans;
    }

    class Node {
        String str;
        int val;

        Node(String _str, int _val) {
            str = _str;
            val = _val;
        }
    }
}
