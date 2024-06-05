import java.util.*;

/**
 * @ClassName: TopologicalSort
 * @Description: 拓扑排序
 * @Author: zhaooo
 * @Date: 2022/7/23/12:40
 */
public class TopologicalSort {
    // 图的表示方法有：
    // 邻接表，邻接矩阵，邻接多重表，边集数组
    // 为了简单表示使用Map<Integer,set<Integer>>或者set<Integer>[]数组表示 如果所有点在[1,n]之间用数组更方便
    // graph保证有所有点，即使某一点出度为0
    public List<Integer> topologicalSort(Map<Integer, Set<Integer>> graph) {
        // 记录入度为0的点
        Queue<Integer> queue = new ArrayDeque<>();
        // 点到入度映射 如果元所有点在[1,n]之间 则inDegree=int[n+1]
        Map<Integer, Integer> inDegree = new HashMap<>();
        List<Integer> res = new ArrayList<>();
        // 构建入度表
        graph.forEach((key, vals) -> {
            // 防止漏点
            if (!inDegree.containsKey(key)) {
                inDegree.put(key, 0);
            }
            vals.forEach(v -> {
                inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
            });
        });
        inDegree.forEach((k, v) -> {
            if (v == 0) {
                queue.add(k);
            }
        });
        while (!queue.isEmpty()) {
            // 入度为0的点超过一个拓扑排序不唯一
            if (queue.size() > 1) {
                return null;
            }
            int cur = queue.poll();
            res.add(cur);
            // 对应点的入度减一
            graph.get(cur).forEach(v -> {
                int tmpDegree = inDegree.get(v) - 1;
                if (tmpDegree == 0) {
                    queue.add(v);
                }
                inDegree.put(v, tmpDegree);
            });
        }
        // 长度不相等则还有元素剩下，即图有环
        return res.size() == graph.size() ? res : null;
    }
}
