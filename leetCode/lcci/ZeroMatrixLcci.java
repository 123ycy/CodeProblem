package lcci;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ZeroMatrixLcci
 * @Description: 面试题 01.08. 零矩阵
 * @Author: zhaooo
 * @Date: 2022/9/30/11:10
 */
public class ZeroMatrixLcci {
    /**
     * 数组记录
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    list.add(i);
                    list.add(j);
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            int i1 = list.get(i++);
            int j1 = list.get(i);
            set(matrix, i1, j1, m, n);
        }
    }

    void set(int[][] matrix, int i, int j, int m, int n) {
        for (int i1 = 0; i1 < m; i1++) {
            matrix[i1][j] = 0;
        }
        for (int j1 = 0; j1 < n; j1++) {
            matrix[i][j1] = 0;
        }
    }

    /**
     * 原地修改
     * @param matrix
     */
    public void setZeroes2(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean flagCol0 = false, flagRow0 = false;
        for (int[] ints : matrix) {
            if (ints[0] == 0) {
                flagCol0 = true;
                break;
            }
        }
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                flagRow0 = true;
                break;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (flagCol0) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
        if (flagRow0) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
    }
}
