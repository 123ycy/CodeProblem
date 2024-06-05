package normal;

import java.util.HashSet;

/**
 * @ClassName: UniqueEmailAddresses
 * @Description: 929. 独特的电子邮件地址
 * @Author: zhaooo
 * @Date: 2022/6/4/8:07
 */
public class UniqueEmailAddresses {
    /**
     * 扫描
     * @param emails
     * @return
     */
    public int numUniqueEmails(String[] emails) {
        HashSet<String> set = new HashSet<>();
        for (String email : emails) {
            StringBuilder sb = new StringBuilder();
            char[] chars = email.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '.') {
                    continue;
                }
                if (chars[i] == '+') {
                    while (chars[i] != '@') {
                        i++;
                    }
                }
                if (chars[i] == '@') {
                    sb.append(chars, i, chars.length - i);
                    break;
                }
                sb.append(chars[i]);
            }
            set.add(sb.toString());
        }
        return set.size();
    }

    /**
     * 正则表达式
     * @param emails
     * @return
     */
    public int numUniqueEmails2(String[] emails) {
        String regex = "(?:\\.)(?=.*@)|(?:\\+.*)(?=@)";
        HashSet<String> set = new HashSet<>();
        for (String email : emails) {
            set.add(email.replaceAll(regex, ""));
        }
        return set.size();
    }
}
