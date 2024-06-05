package normal;

/**
 * @ClassName: SetMatrixZeroes
 * @Description: 73. 矩阵置零
 * @Author: zhaooo
 * @Date: 2023/7/13/9:46
 */
public class SetMatrixZeroes {
    /**
     * 位图
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] set = new int[2][10];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    set[0][i / 20] |= 1 << i % 20;
                    set[1][j / 20] |= 1 << j % 20;
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            if (set[0][i] != 0) {
                setRow(i, set[0][i], matrix);
            }
            if (set[1][i] != 0) {
                setColumn(i, set[1][i], matrix);
            }
        }
    }

    void setRow(int index, int row, int[][] matrix) {
        for (int i = 0; i < 20; i++) {
            if ((row & 1 << i) > 0 && index * 20 + i < matrix.length) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[index * 20 + i][j] = 0;
                }
            }
        }
    }

    /**
     * 用矩阵的第一行和第一列作为标记数组
     * @param index
     * @param column
     * @param matrix
     */
    void setColumn(int index, int column, int[][] matrix) {
        for (int i = 0; i < 20; i++) {
            if ((column & 1 << i) > 0 && index * 20 + i < matrix[0].length) {
                for (int j = 0; j < matrix.length; j++) {
                    matrix[j][index * 20 + i] = 0;
                }
            }
        }
    }

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