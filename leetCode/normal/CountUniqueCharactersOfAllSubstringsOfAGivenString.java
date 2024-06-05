package normal;

import java.util.*;

/**
 * @ClassName: CountUniqueCharactersOfAllSubstringsOfAGivenString
 * @Description: 828. 统计子串中的唯一字符
 * https://leetcode.cn/problems/count-unique-characters-of-all-substrings-of-a-given-string/solution/by-muse-77-v7cs/
 * @Author: zhaooo
 * @Date: 2022/9/6/9:23
 */
public class CountUniqueCharactersOfAllSubstringsOfAGivenString {

    /**
     * 数学
     * @param s
     * @return
     */
    public int uniqueLetterString(String s) {
        Map<Character, List<Integer>> map = new HashMap<>();
        char[] sc = s.toCharArray();
        // 记录每个字符出现的次数
        for (int i = 0; i < sc.length; i++) {
            if (!map.containsKey(sc[i])) {
                map.put(sc[i], new ArrayList<>());
            }
            map.get(sc[i]).add(i);
        }
        int result = 0;
        for (Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
            int head = -1, tail = -1;
            List<Integer> item = entry.getValue();
            for (int i = 0; i < item.size(); i++) {
                // 最后一个的tail为sc.length
                tail = (i < item.size() - 1) ? item.get(i + 1) : sc.length;
                // 距离头部长度 * 距离尾部长度
                result += (item.get(i) - head) * (tail - item.get(i));
                head = item.get(i);
            }
        }
        return result;
    }

    /**
     * 数学
     * 数组优化
     * @param s
     * @return
     */
    public int uniqueLetterString2(String s) {
        int ans = 0;
        //存储某一个字符前一个字符所在位置
        int[] pre = new int[26];
        //存储某个字符当前所处位置
        int[] cur = new int[26];

        Arrays.fill(pre, -1);
        Arrays.fill(cur, -1);

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'A';
            // 非第一次出现即多次出现，算当前区间该字母出现次数
            if (cur[index] > -1) {
                // 计算该[字母上一次出现位置，当前位置]区间该字母出现次数
                ans += (i - cur[index]) * (cur[index] - pre[index]);
            }
            pre[index] = cur[index];
            cur[index] = i;
        }
        for (int i = 0; i < 26; i++) {
            if (cur[i] > -1) {
                ans += (cur[i] - pre[i]) * (s.length() - cur[i]);
            }
        }
        return ans;
    }
}
