package normal;

/**
 * @ClassName: AddBinary
 * @Description: 67. 二进制求和
 * @Author: zhaooo
 * @Date: 2023/8/23/9:43
 */
public class AddBinary {
    /**
     * 加法
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        int idxA = a.length();
        int idxB = b.length();
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        while (idxA > 0 || idxB > 0) {
            int cur = (idxA > 0 ? a.charAt(--idxA) - '0' : 0) + (idxB > 0 ? b.charAt(--idxB) - '0' : 0) + carry;
            // 11B 或者 10B 时为1
            carry = cur > 1 ? 1 : 0;
            sb.append(cur & 1);
        }
        // 最后一位
        if (carry > 0) {
            sb.append(1);
        }
        return sb.reverse().toString();
    }

    /**
     * 位运算
     * A  B  ^  &  +
     * 0  0  0  0  0+0=00
     * 0  1  1  0  0+1=01
     * 1  0  1  0  1+0=01
     * 1  1  0  1  1+1=10
     * 如果字符串超过 33 位，不能转化为 Integer
     * 如果字符串超过 65 位，不能转化为 Long
     * 如果字符串超过 500000001 位，不能转化为 BigInteger
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary2(String a, String b) {
        int x = Integer.valueOf(a, 2);
        int y = Integer.valueOf(b, 2);
        while (y > 0) {
            int ans = x ^ y;
            int carry = (x & y) >> 1;
            x = ans;
            y = carry;
        }
        return Integer.toBinaryString(x);
    }
}
