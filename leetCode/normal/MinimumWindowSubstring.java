package normal;

/**
 * @ClassName: MinimumWindowSubstring
 * @Description: 76. 最小覆盖子串
 * @Author: zhaooo
 * @Date: 2023/10/13 00:20
 */
public class MinimumWindowSubstring {
    /**
     * 滑动窗口
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) {
            return "";
        }
        // 窗口和t不覆盖字符的个数
        int diffs = 0;
        int l = 0, r = 0, ansL = 0, ansR = 0;
        // 记录s中字符和t中字符个数差
        int[] map = new int[58];
        for (int i = 0; i < n; i++) {
            // 因为s还未遍历，故map[idx]--
            if (map[t.charAt(i) - 'A']-- == 0) {
                diffs++;
            }
        }
        while (r < m) {
            int rIdx = s.charAt(r) - 'A';
            // 刚好完成一种字符的覆盖
            if (++map[rIdx] == 0) {
                diffs--;
            }
            while (l <= r) {
                int lIdx = s.charAt(l) - 'A';
                // map[lIdx] > 0表示lIdx对应的字符是多余的
                if (map[lIdx] > 0) {
                    map[lIdx]--;
                    l++;
                } else {
                    break;
                }
            }
            // 在修改ans之前r++因为substring(l,r)对应[l,r)
            r++;
            if (diffs == 0) {
                if (ansR == 0 || (r - l) < (ansR - ansL)) {
                    ansL = l;
                    ansR = r;
                }
            }
        }
        return s.substring(ansL, ansR);
    }
}
