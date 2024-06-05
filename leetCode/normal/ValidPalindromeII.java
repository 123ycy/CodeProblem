package normal;

/**
 * @ClassName: ValidPalindromeII
 * @Description: 680. 验证回文串 II
 * @Author: zhaooo
 * @Date: 2023/10/13 12:39
 */
public class ValidPalindromeII {
    int maxDepth;
    String str;

    /**
     * dfs
     *
     * @param s
     * @return
     */
    public boolean validPalindrome(String s) {
        maxDepth = 1;
        str = s;
        return valid(0, s.length() - 1, 0);
    }

    boolean valid(int l, int r, int depth) {
        if (depth > maxDepth) {
            return false;
        }
        while (l < r) {
            if (str.charAt(l) == str.charAt(r)) {
                l++;
                r--;
            } else {
                return valid(l + 1, r, depth + 1) || valid(l, r - 1, depth + 1);
            }
        }
        return true;
    }
}
