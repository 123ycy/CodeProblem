package normal;

import java.util.Arrays;

/**
 * @ClassName: RotatedDigits
 * @Description: 788. 旋转数字
 * @Author: zhaooo
 * @Date: 2022/9/25/10:24
 */
public class RotatedDigits {
    int[] n1 = new int[]{0, 1, 8};
    int[] n2 = new int[]{2, 5, 6, 9};
    int ans = 0, n;
    static final int[] DIFFS = {0, 0, 1, -1, -1, 1, 1, -1, 0, 1};
    char[] s;
    int[][] dp;

    /**
     * dfs
     * @param n
     * @return
     */
    public int rotatedDigits(int n) {
        this.n = n;
        // 掠过开始的0
        for (int i = 1; i < 3; i++) {
            dfs(n1[i], false);
        }
        for (int i = 0; i < 2; i++) {
            dfs(n2[i], true);
        }
        return ans;
    }

    void dfs(int cur, boolean containN2) {
        if (cur > n) {
            return;
        }
        if (containN2) {
            ans++;
        }
        cur *= 10;
        for (int i : n1) {
            dfs(cur + i, containN2);
        }
        for (int i : n2) {
            dfs(cur + i, true);
        }
    }

    /**
     * 数位dp
     * @param n
     * @return
     */
    public int rotatedDigits2(int n) {
        s = String.valueOf(n).toCharArray();
        int m = s.length;
        dp = new int[m][2];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], -1);
        }
        return f(0, 0, true);
    }

    int f(int i, int hasDiff, boolean isLimit) {
        // 只有包含 2/5/6/9 才算一个好数
        if (i == s.length) {
            return hasDiff;
        }
        if (!isLimit && dp[i][hasDiff] >= 0) {
            return dp[i][hasDiff];
        }
        int res = 0;
        int up = isLimit ? s[i] - '0' : 9;
        // 枚举要填入的数字 d
        for (int d = 0; d <= up; ++d) {
            // d 不是 3/4/7
            if (DIFFS[d] != -1) {
                res += f(i + 1, hasDiff | DIFFS[d], isLimit && d == up);
            }
        }
        if (!isLimit) {
            dp[i][hasDiff] = res;
        }
        return res;
    }
}
