package pointSwordAtOfferII;

import java.util.*;

/**
 * @ClassName: T115
 * @Description: 剑指 Offer II 115. 重建序列
 * @Author: zhaooo
 * @Date: 2022/7/23/11:30
 */
public class T115 {
    /**
     * 拓扑排序
     * @param nums
     * @param sequences
     * @return
     */
    public boolean sequenceReconstruction(int[] nums, int[][] sequences) {
        //转化成边
        Map<Integer, Set<Integer>> edge = new HashMap<>();
        //记录入度
        int[] inDegree = new int[nums.length + 1];
        for (int[] sequence : sequences) {
            //注意这里是一个子序列，有多个元素，不是两个
            for (int i = 1; i < sequence.length; i++) {
                int from = sequence[i - 1];
                int to = sequence[i];
                //判断是否有此条边
                if (edge.containsKey(from) && edge.get(from).contains(to)) {
                    continue;
                }
                edge.putIfAbsent(from, new HashSet<>());
                edge.get(from).add(to);
                inDegree[to]++;
            }
        }
        //记录入度为0的点
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= nums.length; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            //存在多个入度为0的点 会有多个超序列 直接返回false
            if (queue.size() > 1) {
                return false;
            }
            int from = queue.poll();
            Set<Integer> to = edge.get(from);
            if (to == null) {
                continue;
            }
            //和此点连通的点入度减一
            for (int point : to) {
                inDegree[point]--;
                // 只有此时入度才可能改变
                if (inDegree[point] == 0) {
                    queue.add(point);
                }
            }
        }
        return true;
    }

    /**
     * 树判断是否存在对应路径
     * @param nums
     * @param sequences
     * @return
     */
    public boolean sequenceReconstruction2(int[] nums, int[][] sequences) {
        // 用map表示树
        Set<Integer>[] tree = new Set[nums.length + 1];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new HashSet<>();
        }
        for (int[] sequence : sequences) {
            for (int i = 1; i < sequence.length; i++) {
                tree[sequence[i - 1]].add(sequence[i]);
            }
        }
        // 如果不存在一条从nums[0]到nums[len-1]的路径即不符合
        // 有多个超序列则不能遍历到底，即edge不能满足这个限制
        // nums过长则edge不包含这个元素
        for (int i = 1; i < nums.length; i++) {
            if (!tree[nums[i - 1]].contains(nums[i])) {
                return false;
            }
        }
        return true;
    }
}
