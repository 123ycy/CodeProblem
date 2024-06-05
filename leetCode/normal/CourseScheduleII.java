package normal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @ClassName: CourseScheduleII
 * @Description: 210. 课程表 II
 * @Author: zhaooo
 * @Date: 2023/07/31 15:06
 */
public class CourseScheduleII {
    /**
     * BFS 拓扑排序
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses == 0) return new int[0];
        int[] inDegrees = new int[numCourses];
        // 建立入度表
        for (int[] p : prerequisites) { // 对于有先修课的课程，计算有几门先修课
            inDegrees[p[0]]++;
        }
        // 入度为0的节点队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegrees.length; i++) {
            if (inDegrees[i] == 0) queue.offer(i);
        }
        int count = 0;  // 记录可以学完的课程数量
        int[] res = new int[numCourses];  // 可以学完的课程
        // 根据提供的先修课列表，删除入度为 0 的节点
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            res[count++] = curr;   // 将可以学完的课程加入结果当中
            for (int[] p : prerequisites) {
                if (p[1] == curr) {
                    inDegrees[p[0]]--;
                    if (inDegrees[p[0]] == 0) queue.offer(p[0]);
                }
            }
        }
        if (count == numCourses) return res;
        return new int[0];
    }

    /**
     * 邻接矩阵 + DFS
     * DFS 访问每一个课程，若存在环直接返回 status 保存课程的访问状态，同一个栈保存课程的访问序列。
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        if (numCourses == 0) return new int[0];
        // 建立邻接矩阵
        int[][] graph = new int[numCourses][numCourses];
        for (int[] p : prerequisites) {
            // 其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
            // bi --> ai
            graph[p[1]][p[0]] = 1;
        }
        // 记录访问状态的数组，访问过了标记 -1，正在访问标记 1，还未访问标记 0
        int[] status = new int[numCourses];
        Stack<Integer> stack = new Stack<>();  // 用栈保存访问序列
        for (int i = 0; i < numCourses; i++) {
            if (!dfs(graph, status, i, stack)) return new int[0]; // 只要存在环就返回
        }
        int[] res = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            // 先入栈的是后续节点少的，即后修课程少的，先修课程多的
            // 后入栈的是先修课程少的，即先出栈的是先修课程少的
            res[i] = stack.pop();
        }
        return res;
    }

    private boolean dfs(int[][] graph, int[] status, int i, Stack<Integer> stack) {
        // 当前节点在此次 dfs 中正在访问，说明存在环
        if (status[i] == 1) return false;
        if (status[i] == -1) return true;
        // if(status[i] == 0)
        // 标记为正在访问
        status[i] = 1;
        for (int j = 0; j < graph.length; j++) {
            //  i-->j 即找i的后续课程
            if (graph[i][j] == 1 && !dfs(graph, status, j, stack)) return false;
        }
        // 标记为已访问
        status[i] = -1;
        // 先访问完的是内层dfs，图中后续节点少的
        stack.push(i);
        return true;
    }
}
