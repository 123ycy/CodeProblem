package normal;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @ClassName: KthSmallestElementInASortedMatrix
 * @Description: 378. 有序矩阵中第 K 小的元素
 * @Author: zhaooo
 * @Date: 2024/03/15 09:44
 */
public class KthSmallestElementInASortedMatrix {
    /**
     * 堆排序
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> matrix[o[0]][o[1]]));
        for (int i = 0; i < n; i++) {
            priorityQueue.add(new int[]{i, 0});
        }
        int[] cur = new int[2];
        for (int i = 0; i < k; i++) {
            cur = priorityQueue.remove();
            if (cur[1] != n - 1) {
                priorityQueue.add(new int[]{cur[0], cur[1] + 1});
            }
        }
        return matrix[cur[0]][cur[1]];
    }

    /**
     * 矩阵性质 + 二分查找
     * https://leetcode.cn/problems/kth-smallest-element-in-a-sorted-matrix/solutions/311472/you-xu-ju-zhen-zhong-di-kxiao-de-yuan-su-by-leetco
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest2(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];
        // 循环不变量
        // [ < l ][l, mk, r][ > r] 其中[l, r]窗口中一定包含mk
        // [m1, .., mk, .., mn]
        // m是matrix排序后数组，其中len(matrix,l-1)<k 且 len(matrix,r)>=k

        // 退出条件
        // l == r 即 [l, r]窗口中只有一个元素mk

        // 循环体
        // 不断缩小[l, r]长度
        // <=mid部分长度<k : l = mid + 1;
        // <=mid部分长度>=k: r = mid

        // 返回值
        // l或r
        while (left < right) {
            int mid = (left + right) >> 1;
            if (len(matrix, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 小于等于mid的长度，mid不一定在matrix中
    public int len(int[][] matrix, int mid) {
        int n = matrix.length, num = 0;
        int i = n - 1, j = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {
                num += i + 1;
                j++;
            } else {
                i--;
            }
        }
        return num;
    }
}
