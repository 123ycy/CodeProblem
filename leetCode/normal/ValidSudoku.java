package normal;

/**
 * @ClassName: ValidSudoku
 * @Description: 36. 有效的数独
 * @Author: zhaooo
 * @Date: 2023/07/12 09:23
 */
public class ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        int[] map;
        for (int i = 0; i < 9; i++) {
            // 行 列 块的位图
            map = new int[3];
            for (int j = 0; j < 9; j++) {
                // cur[i]分别对应行 列 块
                int[] cur = new int[]{board[i][j] - '1', board[j][i] - '1', board[i / 3 * 3 + j / 3][i % 3 * 3 + j % 3] - '1'};
                for (int k = 0; k < 3; k++) {
                    if (cur[k] < 0) continue;
                    if ((map[k] & 1 << cur[k]) > 0) return false;
                    map[k] |= 1 << cur[k];
                }
            }
        }
        return true;
    }
}