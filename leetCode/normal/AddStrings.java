package normal;

/**
 * @ClassName: AddStrings
 * @Description: 415. 字符串相加
 * @Author: zhaooo
 * @Date: 2024/02/12 23:44
 */
public class AddStrings {
    /**
     * 模拟
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings(String num1, String num2) {
        // num1最长
        if (num2.length() > num1.length()) {
            String tmp = num1;
            num1 = num2;
            num2 = tmp;
        }
        // 其中一个数为0
        if (num2.equals("0")) {
            return num1;
        }
        StringBuilder ans = new StringBuilder();
        int len1 = num1.length(), len2 = num2.length();
        int x = 0, y = 0, carry = 0;
        for (int i = 1; i <= len1; i++) {
            x = num1.charAt(len1 - i) - '0';
            y = i <= len2 ? num2.charAt(len2 - i) - '0' : 0;
            int tmp = x + y + carry;
            ans.append(tmp % 10);
            carry = tmp / 10;
        }
        if (carry != 0) {
            ans.append(carry);
        }
        // reverse保证顺序，因为先添加的低位
        return ans.reverse().toString();
    }

    /**
     * 模拟 优化
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings2(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0, i = num1.length() - 1, j = num2.length() - 1;
        // 只关注carry
        while (i >= 0 || j >= 0 || carry != 0) {
            if (i >= 0) {
                carry += num1.charAt(i--) - '0';
            }
            if (j >= 0) {
                carry += num2.charAt(j--) - '0';
            }
            sb.append(carry % 10);
            carry /= 10;
        }
        return sb.reverse().toString();
    }
}
