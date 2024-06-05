package normal;

/**
 * @ClassName: MergeStringsAlternately
 * @Description: 1768. 交替合并字符串
 * @Author: zhaooo
 * @Date: 2023/09/06 13:53
 */
public class MergeStringsAlternately {
    /**
     * 递归
     *
     * @param word1
     * @param word2
     * @return
     */
    public String mergeAlternately(String word1, String word2) {
        if (word1.isEmpty()) {
            return word2;
        }
        if (word2.isEmpty()) {
            return word1;
        }
        return String.valueOf(word1.charAt(0)) + word2.charAt(0)
                + mergeAlternately(word1.substring(1), word2.substring(1));
    }

    /**
     * 遍历
     *
     * @param word1
     * @param word2
     * @return
     */
    public String mergeAlternately2(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int idx = 0, i1 = 0, i2 = 0;
        char[] res = new char[len1 + len2];
        while (idx < len1 + len2) {
            if (i1 < len1) {
                res[idx++] = word1.charAt(i1++);
            }
            if (i2 < len2) {
                res[idx++] = word2.charAt(i2++);
            }
        }
        return String.valueOf(res);
    }
}
