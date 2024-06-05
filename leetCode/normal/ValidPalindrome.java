package normal;

/**
 * @ClassName: ValidPalindrome
 * @Description: 125. 验证回文串
 * @Author: zhaooo
 * @Date: 2023/07/08 10:57
 */
public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        int l = 0, r = chars.length - 1;
        while (l < r) {
            while (l < r && !Character.isLetterOrDigit(chars[l])) {
                l++;
            }
            while (l < r && !Character.isLetterOrDigit(chars[r])) {
                r--;
            }
            if (Character.toLowerCase(chars[l++]) != Character.toLowerCase(chars[r--])) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome2(String s) {
        // 使用正则表达式移除所有非字母数字字符
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        // 将字符串转换为小写
        s = s.toLowerCase();
        // 判断字符串是否和它的反转相等
        return s.contentEquals(new StringBuilder(s).reverse());
    }
}
