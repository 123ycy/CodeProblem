package normal;

import java.util.*;

/**
 * @ClassName: OpenTheLock
 * @Description: 752. 打开转盘锁 
 * @Author: zhaooo
 * @Date: 2022/9/22/19:47
 */
public class OpenTheLock {
    char[] tgt;
    // dirs改为{1，-1}会有一个A*算法有一个测试用例不能通过
    final int[] dirs = new int[]{-1, 1};

    String t, s;
    Set<String> set = new HashSet<>();

    /**
     * A*算法
     * @param deadends
     * @param target
     * @return
     */
    public int openLock(String[] deadends, String target) {
        String start = "0000";
        if (target.equals(start)) {
            return 0;
        }
        this.tgt = target.toCharArray();
        // 记录不可达状态
        Set<String> set = new HashSet<>(Arrays.asList(deadends));
        if (set.contains(start)) {
            return -1;
        }
        // 记录状态步数
        Map<String, Integer> map = new HashMap<>();
        // 起点到当前状态+当前状态到终点 预估代价的优先队列
        Queue<String> pq = new PriorityQueue<>((s1, s2) -> {
            int f1 = f(s1), f2 = f(s2), h1 = map.get(s1), h2 = map.get(s2);
            return f1 + h1 - f2 - h2;
        });
        map.put(start, 0);
        pq.add(start);
        while (!pq.isEmpty()) {
            String cur = pq.poll();
            int step = map.get(cur);
            char[] chars = cur.toCharArray();
            for (int i = 0; i < 4; i++) {
                char chI = chars[i];
                for (int dir : dirs) {
                    add(chars, i, dir);
                    String next = String.valueOf(chars);
                    chars[i] = chI;
                    if (next.equals(target)) {
                        return step + 1;
                    }
                    if (set.contains(next) || (map.containsKey(next) && map.get(next) < step + 1)) {
                        continue;
                    }
                    map.put(next, step + 1);
                    pq.add(next);
                }
            }
        }
        return -1;
    }

    int f(String str) {
        int ans = 0;
        for (int i = 0; i < 4; i++) {
            int diff = Math.abs(tgt[i] - str.charAt(i));
            ans += Math.min(diff, 10 - diff);
        }
        return ans;
    }

    void add(char[] chars, int i, int add) {
        chars[i] += add;
        // 9之后的ASCII
        if (chars[i] == ':') {
            chars[i] = '0';
            // 0之前的ASCII
        } else if (chars[i] == '/') {
            chars[i] = '9';
        }
    }

    /**
     * 双向bfs
     * @param deadends
     * @param target
     * @return
     */
    public int openLock2(String[] deadends, String target) {
        s = "0000";
        t = target;
        if (s.equals(t)) {
            return 0;
        }
        Collections.addAll(set, deadends);
        if (set.contains(s)) {
            return -1;
        }
        return bfs();
    }

    int bfs() {
        // s2e 代表从起点 s 开始搜索（正向）
        // e2s 代表从结尾 t 开始搜索（反向）
        Deque<String> s2e = new ArrayDeque<>(), e2s = new ArrayDeque<>();
        // m1和m2分别记录两个方向出现的状态和step的映射
        Map<String, Integer> m1 = new HashMap<>(), m2 = new HashMap<>();
        s2e.addLast(s);
        m1.put(s, 0);
        e2s.addLast(t);
        m2.put(t, 0);
        // 只有两个队列都不空，才有必要继续往下搜索
        // 如果其中一个队列空了，说明从某个方向搜到底都搜不到该方向的目标节点
        while (!s2e.isEmpty() && !e2s.isEmpty()) {
            int t;
            // 先更新小的队列
            if (s2e.size() <= e2s.size()) {
                t = update(s2e, m1, m2);
            } else {
                t = update(e2s, m2, m1);
            }
            if (t != -1) {
                return t;
            }
        }
        return -1;
    }

    // 更新一层
    int update(Deque<String> deque, Map<String, Integer> cur, Map<String, Integer> other) {
        int m = deque.size();
        while (m-- > 0) {
            String poll = deque.removeFirst();
            char[] chars = poll.toCharArray();
            int step = cur.get(poll);
            // 枚举替换哪个字符
            for (int i = 0; i < 4; i++) {
                char tmp = chars[i];
                for (int dir : dirs) {
                    // 求得替换字符串 str
                    add(chars, i, dir);
                    String str = String.valueOf(chars);
                    chars[i] = tmp;
                    // 如果str再死亡数字里或者已经访问过则跳过
                    if (set.contains(str) || cur.containsKey(str)) {
                        continue;
                    }
                    // 如果在「另一方向」找到过，说明找到了最短路，否则加入队列
                    if (other.containsKey(str)) {
                        return step + 1 + other.get(str);
                    } else {
                        deque.addLast(str);
                        cur.put(str, step + 1);
                    }
                }
            }
        }
        return -1;
    }
}
