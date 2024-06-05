package normal;

/**
 * @ClassName: LongestPalindromicSubstring
 * @Description: 5. 最长回文子串
 * @Author: zhaooo
 * @Date: 2024/01/19 20:44
 */
public class LongestPalindromicSubstring {

    /**
     * 中心扩散
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) return s;
        // 记录左右节点
        int[] res = new int[2];
        for (int i = 0; i < s.length() - 1; i++) {
            int[] odd = centerSpread(s, i, i);
            int[] even = centerSpread(s, i, i + 1);
            if (odd[1] - odd[0] > res[1] - res[0]) {
                res = odd;
            }
            if (even[1] - even[0] > res[1] - res[0]) {
                res = even;
            }
        }
        return s.substring(res[0], res[1] + 1);
    }

    private int[] centerSpread(String s, int left, int right) {
        int len = s.length();
        while (left >= 0 && right < len) {
            if (s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            } else {
                break;
            }
        }
        return new int[]{left + 1, right - 1};
    }

    /**
     * 动态规划
     * @param s
     * @return
     */
    public String longestPalindrome2(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        char[] chars = s.toCharArray();
        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = chars[i] == chars[i + 1];
            if (dp[i][i + 1]) {
                maxLen = 2;
                begin = i;
            }
        }
        // 递推开始
        // 先枚举子串长度
        for (int L = 3; L <= len; L++) {
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (int i = 0; i < len - L + 1; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i - 1;
                if (chars[i] != chars[j]) {
                    dp[i][j] = false;
                } else {
                    dp[i][j] = dp[i + 1][j - 1];
                }
                // 只要 dp[i][j] == true 成立，就表示子串 s[i..j] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && L > maxLen) {
                    maxLen = L;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * Manacher 算法
     * 添加*保证回文子串是奇数串 一次遍历
     * @param s
     * @return
     */
    public String longestPalindrome3(String s) {
        StringBuilder sb = new StringBuilder("*");
        // 构成[*,a,*,a,*] 保证回文子串是奇数串
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i)).append("*");
        }
        // dp[i]表示在i处能向一个方向延申几步还是回文子串 值为 边界下标-中间下标
        int[] dp = new int[sb.length()];
        char[] chars = sb.toString().toCharArray();
        // bound是已经确定的回文串的最右位置，center是该串的中心
        // max是最大的dp[x] ret是对应x
        int bound = 0, center = 0, max = 0, ret = 0;
        for (int i = 0; i < sb.length(); i++) {
            int r = i;
            if (i <= bound) {
                // 取 bound和i相对于center的对称位置的回文子串的边界 的较小值
                // 超出bound边界的部分不能由对称确定元素
                r = Math.min(bound, i + dp[2 * center - i]);
            }
            // 向两侧延申
            while (r < chars.length && 2 * i - r >= 0 && chars[r] == chars[2 * i - r]) {
                dp[i] = r - i;
                r++;
            }
            if (dp[i] > max) {
                max = dp[i];
                ret = i;
            }
            // 恢复r到回文子串右边界
            if (--r > bound) {
                bound = r;
                center = i;
            }
        }
        // 最后结果一定是[*,...,*] 且该串长度是 中间一个元素（1）+ 2倍一边元素（dp[ret]） 即 1+2*dp[ret]  故非'*'字符的长度是dp[ret]
        // 由于中点是ret，故左侧起点下标为ret-dp[ret]
        // *的下标为i，则右侧的字符对应在原字符串的下标为i/2
        // 起点(ret - dp[ret]) / 2和长度 dp[ret]已知 返回结果
        return s.substring((ret - dp[ret]) / 2, (ret + dp[ret]) / 2);
    }
}
