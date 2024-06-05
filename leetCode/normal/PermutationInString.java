package normal;

/**
 * @ClassName: PermutationInString
 * @Description: 567. 字符串的排列
 * @Author: zhaooo
 * @Date: 2023/10/10 09:33
 */
public class PermutationInString {
    /**
     * 滑动窗口
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        int[] map = new int[26];
        // 记录不同的个数
        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            map[s2.charAt(i) - 'a']++;
            map[s1.charAt(i) - 'a']--;
        }
        for (int i : map) {
            if (i != 0) {
                diff++;
            }
        }
        if (diff == 0) {
            return true;
        }
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            int idx1 = s2.charAt(i) - 'a';
            int idx2 = s2.charAt(i + s1.length()) - 'a';
            // map与diff修改
            diff += --map[idx1] == 0 ? -1 : map[idx1] == -1 ? 1 : 0;
            diff += ++map[idx2] == 0 ? -1 : map[idx2] == 1 ? 1 : 0;
            if (diff == 0) {
                return true;
            }
        }
        return false;
    }
}