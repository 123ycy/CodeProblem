package normal;

/**
 * @ClassName: SudokuSolver
 * @Description: 37. 解数独
 * @Author: zhaooo
 * @Date: 2023/04/01 13:31
 */
public class SudokuSolver {
    /**
     * 暴力（顺序遍历、回溯）+状态压缩
     * 优化解法:选择能填的数字最少的格子开始填，这样填错的概率最小，回溯次数也会变少，下方链接
     * https://leetcode.cn/problems/sudoku-solver/solutions/155521/37-by-ikaruga/
     *
     * @param board
     */
    public void solveSudoku(char[][] board) {
        // 用bitSet记录行、列、搜索块的当前选择元素状态
        // 例如dictX[0] = 000000101 表示 第一行有1和3
        int[] dictX = new int[9];
        int[] dictY = new int[9];
        int[] dictB = new int[9];
        // 最后一个填入的位置
        int last = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int offset = board[i][j] - '1';
                if (offset == '.' - '1') {
                    last = i * 9 + j;
                    continue;
                }
                mask(i, j, offset, dictX, dictY, dictB);
            }
        }
        dfs(0, last, board, dictX, dictY, dictB);
    }

    boolean dfs(int cur, int last, char[][] board, int[] dictX, int[] dictY, int[] dictB) {
        // 注意退出状态不能是 cur == 81，此时正确结果已经访问过，回溯后填入的值清空
        if (cur == last + 1) {
            return true;
        }
        int i = 0, j = 0;
        // 找到下一个要填入的位置
        for (; cur < 81; cur++) {
            i = cur / 9;
            j = cur % 9;
            if (board[i][j] == '.') {
                break;
            }
        }
        for (int offset = 0; offset < 9; offset++) {
            int maskVal = 1 << offset;
            // 数字(offset+1)行、列、块内只能出现一次
            if ((dictX[i] & maskVal) == 0 && (dictY[j] & maskVal) == 0 && (dictB[getBlock(i, j)] & maskVal) == 0) {
                mask(i, j, offset, dictX, dictY, dictB);
                board[i][j] = (char) (offset + '1');
                // 如果当前填入值可以成功，直接退出，保证填入的结果不会被回溯，即不会被清空
                if (dfs(cur + 1, last, board, dictX, dictY, dictB)) {
                    return true;
                }
                // 回溯
                unmask(i, j, offset, dictX, dictY, dictB);
                board[i][j] = '.';
            }
        }
        return false;
    }

    // 记录状态
    void mask(int i, int j, int offset, int[] dictX, int[] dictY, int[] dictB) {
        dictX[i] |= 1 << offset;
        dictY[j] |= 1 << offset;
        dictB[getBlock(i, j)] |= 1 << offset;
    }

    // 回复状态
    void unmask(int i, int j, int offset, int[] dictX, int[] dictY, int[] dictB) {
        dictX[i] &= ~(1 << offset);
        dictY[j] &= ~(1 << offset);
        dictB[(i / 3) * 3 + j / 3] &= ~(1 << offset);
    }

    // 获得块号
    int getBlock(int i, int j) {
        return (i / 3) * 3 + j / 3;
    }
}
