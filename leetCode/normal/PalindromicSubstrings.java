package normal;

/**
 * @ClassName: PalindromicSubstrings
 * @Description: 647. 回文子串
 * @Author: zhaooo
 * @Date: 2023/10/13 13:01
 */
public class PalindromicSubstrings {
    /**
     * 中心拓展
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        int n = s.length(), ans = 0;
        for (int i = 0; i < 2 * n - 1; ++i) {
            int l = i / 2, r = i / 2 + i % 2;
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                --l;
                ++r;
                ++ans;
            }
        }
        return ans;
    }

    /**
     * Manacher 算法
     *
     * @param s
     * @return
     */
    public int countSubstrings2(String s) {
        int n = s.length();
        // 初始化
        StringBuilder t = new StringBuilder("$#");
        // s每个字母之间填充#，不用考虑中心是1个还是2个字母
        for (int i = 0; i < n; ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        n = t.length();
        // $#...#!   $和!不等，不用考虑越界
        t.append('!');

        // f[i]表示以i为中心的回文子串半径，一个字符半径为1，包含中心元素
        int[] f = new int[n];
        int iMax = 0, rMax = 0, ans = 0;
        for (int i = 1; i < n; ++i) {
            // 初始化 f[i]
            f[i] = i <= rMax ? Math.min(rMax - i + 1, f[2 * iMax - i]) : 1;
            // 中心拓展
            while (t.charAt(i + f[i]) == t.charAt(i - f[i])) {
                ++f[i];
            }
            // 动态维护 iMax 和 rMax，i + f[i] - 1是以i为中心的回文串右边界
            if (i + f[i] - 1 > rMax) {
                iMax = i;
                rMax = i + f[i] - 1;
            }
            // if (s[i] == '#') // 中心为#，回文半径一定是奇数 #a#a#
            // 	ans += (f[i] - 1) / 2;
            // else // 中心是字母，回文半径是偶数 #a#b#a#
            // 	ans += f[i] / 2;
            ans += f[i] / 2;
        }
        return ans;
    }
}
