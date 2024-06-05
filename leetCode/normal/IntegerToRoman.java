package normal;

/**
 * @ClassName: IntegerToRoman
 * @Description: 12. 整数转罗马数字
 * @Author: zhaooo
 * @Date: 2023/7/5/20:19
 */
public class IntegerToRoman {
    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        char[][] cs = new char[][]{{'I', 'V'}, {'X', 'L'}, {'C', 'D'}, {'M', '!'}};
        for (int i = 3; i >= 0; i--) {
            // i表示divisor中0的个数
            int divisor = 1;
            for (int j = 0; j < i; j++) {
                divisor *= 10;
            }
            int quotient = num / divisor;
            // 更新num
            num %= divisor;
            if (quotient == 4) {
                sb.append(cs[i][0]);
                sb.append(cs[i][1]);
                continue;
            }
            if (quotient == 9) {
                sb.append(cs[i][0]);
                sb.append(cs[i + 1][0]);
                continue;
            }
            if (quotient > 4) {
                sb.append(cs[i][1]);
                quotient -= 5;
            }
            for (int j = 0; j < quotient; j++) {
                sb.append(cs[i][0]);
            }
        }
        return sb.toString();
    }

    public String intToRoman2(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuffer roman = new StringBuffer();
        for (int i = 0; i < values.length; ++i) {
            int value = values[i];
            String symbol = symbols[i];
            while (num >= value) {
                num -= value;
                roman.append(symbol);
            }
            if (num == 0) {
                break;
            }
        }
        return roman.toString();
    }
}
