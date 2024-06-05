package normal;

/**
 * @ClassName: CountNumberOfHomogenousSubstrings
 * @Description: 1759. 统计同构子字符串的数目
 * @Author: zhaooo
 * @Date: 2022/12/26/11:42
 */
public class CountNumberOfHomogenousSubstrings {
    public int countHomogenous(String s) {
        final int MOD = (int) (1e9 + 7);
        long ret = 0;
        int len = s.length();
        int tmpStart = 0, i = 0;
        char pre = s.charAt(0);
        int[] map = new int[len + 1];
        for (; i < len; i++) {
            char cur = s.charAt(i);
            if (cur != pre) {
                map[i - tmpStart]++;
                tmpStart = i;
                pre = s.charAt(tmpStart);
            }
        }
        map[i - tmpStart]++;
        for (i = 1; i <= len; i++) {
            if (map[i] != 0) {
                ret += map[i] * (long) (i + 1) * i >> 1;
                ret %= MOD;
            }
        }
        return (int) ret;
    }
}
