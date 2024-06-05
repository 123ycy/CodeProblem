package normal;

/**
 * @ClassName: SearchA2dMatrix
 * @Description: 74. 搜索二维矩阵
 * @Author: zhaooo
 * @Date: 2023/08/18 13:11
 */
public class SearchA2dMatrix {
    /**
     * 两次二分
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int low = 0, top = m - 1, l = 0, r = n - 1;
        while (low < top) {
            int mid = (low + top) >> 1;
            if (target < matrix[mid][0]) {
                top = mid - 1;
            } else if (target > matrix[mid][n - 1]) {
                low = mid + 1;
            } else {
                // target在matrix[mid]中，用low保存mid
                low = mid;
                break;
            }
        }
        // 正常二分
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (matrix[low][mid] == target) {
                return true;
            }
            if (matrix[low][mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }

    /**
     * 一次二分
     * 选择最右上的点，向左下靠近，路径把整个矩阵分成2部分
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] < target) {
                // 下移
                row++;
            } else if (matrix[row][col] > target) {
                // 左移
                col--;
            } else {
                return true;
            }
        }
        return false;
    }
}
