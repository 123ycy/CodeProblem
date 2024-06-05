package normal;

/**
 * @ClassName: GreatestCommonDivisorOfStrings
 * @Description: 1071. 字符串的最大公因子
 * @Author: zhaooo
 * @Date: 2023/09/07 10:45
 */
public class GreatestCommonDivisorOfStrings {
    /**
     * 暴力
     *
     * @param str1
     * @param str2
     * @return
     */
    public String gcdOfStrings(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int gcd = gcd(len1, len2);
        for (int i = gcd; i > 0; i--) {
            if (i % (gcd / i) == 0 && check(str1, str2, i)) {
                return str1.substring(0, i);
            }
        }
        return "";
    }

    boolean check(String str1, String str2, int cd) {
        int q1 = str1.length() / cd;
        int q2 = str2.length() / cd;
        for (int i = 0; i < cd; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
            for (int j = 1; j < q1; j++) {
                if (str1.charAt(i) != str1.charAt(j * cd + i)) {
                    return false;
                }
            }
            for (int j = 1; j < q2; j++) {
                if (str2.charAt(i) != str2.charAt(j * cd + i)) {
                    return false;
                }
            }
        }
        return true;
    }

    int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * 如果它们有公因子 abc，那么 str1 就是 m 个 abc 的重复，str2 是 n 个 abc 的重复，连起来就是 m+n 个 abc 跟 n+m个 abc 是一样的
     * 当其中有一个字母不同就会发生错位 而对不齐
     * 如果 str1 + str2 == str2 + str1 就意味着有解
     *
     * @param str1
     * @param str2
     * @return
     */
    public String gcdOfStrings2(String str1, String str2) {
        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }
        return str1.substring(0, gcd(str1.length(), str2.length()));
    }
}
