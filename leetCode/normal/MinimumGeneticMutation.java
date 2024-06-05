package normal;

import java.util.*;

/**
 * @ClassName: MinimumGeneticMutation
 * @Description: 433. 最小基因变化
 * @Author: zhaooo
 * @Date: 2023/08/06 13:55
 */
public class MinimumGeneticMutation {
    Set<String> set = new HashSet<>();
    char[] base = new char[]{'A', 'C', 'G', 'T'};

    /**
     * bfs
     *
     * @param S
     * @param T
     * @param bank
     * @return
     */
    public int minMutation(String S, String T, String[] bank) {
        Collections.addAll(set, bank);
        Deque<String> d = new ArrayDeque<>();
        Map<String, Integer> map = new HashMap<>();
        d.addLast(S);
        map.put(S, 0);
        while (!d.isEmpty()) {
            int size = d.size();
            // 每次取出一层
            while (size-- > 0) {
                String s = d.removeFirst();
                char[] cs = s.toCharArray();
                int step = map.get(s);
                for (int i = 0; i < 8; i++) {
                    for (char c : base) {
                        if (cs[i] == c) continue;
                        char[] clone = cs.clone();
                        clone[i] = c;
                        String sub = String.valueOf(clone);
                        // bfs保证已经遍历的是最短路径
                        if (!set.contains(sub) || map.containsKey(sub)) {
                            continue;
                        }
                        if (sub.equals(T)) {
                            return step + 1;
                        }
                        map.put(sub, step + 1);
                        d.addLast(sub);
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 双向bfs
     *
     * @param startGene
     * @param endGene
     * @param bank
     * @return
     */
    public int minMutation2(String startGene, String endGene, String[] bank) {
        set.addAll(Arrays.asList(bank));
        if (!set.contains(endGene)) {
            return -1;
        }
        // start to end;end to start
        Deque<String> s2e = new ArrayDeque<>(), e2s = new ArrayDeque<>();
        HashMap<String, Integer> m1 = new HashMap<>(), m2 = new HashMap<>();
        s2e.addLast(startGene);
        m1.put(startGene, 0);
        e2s.addLast(endGene);
        m2.put(endGene, 0);
        int ret = -1;
        // 队列大的出队，保证两个队列大小差不多
        while (!s2e.isEmpty() && !e2s.isEmpty()) {
            if (m1.size() < m2.size()) {
                ret = update(s2e, m1, m2);
            } else {
                ret = update(e2s, m2, m1);
            }
        }
        return ret;
    }

    int update(Deque<String> gene, Map<String, Integer> m1, Map<String, Integer> m2) {
        if (gene.isEmpty()) {
            return -1;
        }
        String cur = gene.removeFirst();
        if (m2.containsKey(cur)) {
            return m1.get(cur) + m2.get(cur);
        }
        char[] cs = cur.toCharArray();
        for (int i = 0; i < 8; i++) {
            for (char nc : base) {
                char c = cs[i];
                cs[i] = nc;
                String nStr = String.valueOf(cs);
                if (set.contains(nStr) && !m1.containsKey(nStr)) {
                    gene.addLast(nStr);
                    // 也可以在这里判断m2.containsKey(cur)
                    m1.put(nStr, m1.get(cur) + 1);
                }
                cs[i] = c;
            }
        }
        return -1;
    }

    /**
     * A*算法
     *
     * @param startGene
     * @param endGene
     * @param bank
     * @return
     */
    public int minMutation3(String startGene, String endGene, String[] bank) {
        set.addAll(Arrays.asList(bank));
        Map<String, Integer> map = new HashMap<>();
        // 小根堆
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> distance(a, endGene)
                + map.get(a) - distance(b, endGene) + map.get(b));
        pq.add(startGene);
        map.put(startGene, 0);
        while (!pq.isEmpty()) {
            String cur = pq.poll();
            char[] cs = cur.toCharArray();
            for (int i = 0; i < 8; i++) {
                char c = cs[i];
                for (char bc : base) {
                    cs[i] = bc;
                    String nStr = String.valueOf(cs);
                    if (nStr.equals(endGene)) {
                        return map.get(cur) + 1;
                    }
                    if (set.contains(nStr) && !map.containsKey(nStr)) {
                        map.put(nStr, map.get(cur) + 1);
                        pq.add(nStr);
                    }
                }
                cs[i] = c;
            }
        }
        return -1;
    }

    int distance(String s, String t) {
        int dis = 0;
        for (int i = 0; i < 8; i++) {
            if (s.charAt(i) != t.charAt(i)) dis++;
        }
        return dis;
    }
}