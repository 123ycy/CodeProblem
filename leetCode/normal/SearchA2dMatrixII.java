package normal;

/**
 * @ClassName: SearchA2dMatrixII
 * @Description: 240. 搜索二维矩阵 II
 * @Author: zhaooo
 * @Date: 2024/03/15 10:45
 */
public class SearchA2dMatrixII {
    /**
     * 二分
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int i = m - 1, j = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] == target) {
                return true;
            }
            if (matrix[i][j] < target) {
                j++;
            } else {
                i--;
            }
        }
        return false;
    }
}
