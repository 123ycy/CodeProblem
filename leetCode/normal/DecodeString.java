package normal;

/**
 * @ClassName: DecodeString
 * @Description: 394. 字符串解码
 * @Author: zhaooo
 * @Date: 2024/05/04 11:12
 */
public class DecodeString {
    /**
     * dfs
     */
    int i;

    public String decodeString(String s) {
        char[] chars = s.toCharArray();
        return dfs(chars, 0).toString();
    }

    public StringBuilder dfs(char[] chars, int idx) {
        StringBuilder sb = new StringBuilder();
        for (i = idx; i < chars.length && chars[i] != ']'; i++) {
            if (Character.isLetter(chars[i])) {
                sb.append(chars[i]);
            } else {
                StringBuilder numBd = new StringBuilder();
                while (Character.isDigit(chars[i])) {
                    numBd.append(chars[i++]);
                }
                // 此时chars[i]是'['
                StringBuilder tmp = dfs(chars, i + 1);
                for (int k = 0; k < Integer.parseInt(numBd.toString()); k++) {
                    sb.append(tmp.toString());
                }
            }
        }
        // 此时chars[i]是']'
        return sb;
    }


}
