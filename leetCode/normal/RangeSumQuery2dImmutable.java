package normal;

/**
 * @ClassName: RangeSumQuery2dImmutable
 * @Description: 304. 二维区域和检索 - 矩阵不可变
 * @Author: zhaooo
 * @Date: 2023/10/09 16:10
 */
public class RangeSumQuery2dImmutable {
    /**
     * 一维前缀和
     */
    class NumMatrix {
        int[][] matrix;
        int[][] sum;

        public NumMatrix(int[][] matrix) {
            this.matrix = matrix;
            sum = new int[matrix.length][matrix[0].length + 1];
            // 计算前缀和
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    sum[i][j + 1] = sum[i][j] + matrix[i][j];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            int ans = 0;
            for (int i = row1; i <= row2; i++) {
                ans += sum[i][col2 + 1] - sum[i][col1];
            }
            return ans;
        }
    }

    /**
     * 二维前缀和
     * https://leetcode.cn/problems/range-sum-query-2d-immutable/solutions/629187/ru-he-qiu-er-wei-de-qian-zhui-he-yi-ji-y-6c21/
     */
    class NumMatrix2 {
        // sum[i+1][j+1]表示[0,0]到[i,j]的元素和
        int[][] sums;

        public NumMatrix2(int[][] matrix) {
            int m = matrix.length;
            if (m > 0) {
                int n = matrix[0].length;
                sums = new int[m + 1][n + 1];
                // 计算二维前缀和
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        sums[i + 1][j + 1] = sums[i][j + 1] + sums[i + 1][j] - sums[i][j] + matrix[i][j];
                    }
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            // 减去左上和右上的矩阵和，在加上多减去的部分
            return sums[row2 + 1][col2 + 1] - sums[row1][col2 + 1] - sums[row2 + 1][col1] + sums[row1][col1];
        }
    }
}
