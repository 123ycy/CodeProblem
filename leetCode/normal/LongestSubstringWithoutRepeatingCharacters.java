package normal;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;

/**
 * @ClassName: LongestSubstringWithoutRepeatingCharacters
 * @Description: 3. 无重复字符的最长子串
 * @Author: zhaooo
 * @Date: 2023/10/11 13:41
 */
public class LongestSubstringWithoutRepeatingCharacters {

    public String longestSubstring(String s) {
        int maxLen = 0, start = 0;
        int[] map = new int[200];
        Arrays.fill(map, -1);
        int l = 0, r = 0;
        // [0, l)[l, r)
        for (; r < s.length(); r++) {
            int idx = s.charAt(r);
            if (r - l > maxLen) {
                maxLen = r - l;
                start = l;
            }
            if (map[idx] >= 0) {
                l = Math.max(l, map[idx] + 1);
            }
            map[idx] = r;
        }
        if (r - l > maxLen) {
            maxLen = r - l;
            start = l;
        }
        return s.substring(start, maxLen + start);
    }

    // 允许最多重复k次
    public String longestSubstring(String s, int k) {
        int maxLen = 0, start = 0;
        // 记录出现的位置
        HashMap<Character, Deque<Integer>> map = new HashMap<>();
        int l = 0, r = 0;
        // [0, l)[l, r)
        for (; r < s.length(); r++) {
            char idx = s.charAt(r);
            if (r - l > maxLen) {
                maxLen = r - l;
                start = l;
            }
            if (!map.containsKey(idx)) {
                map.put(idx, new ArrayDeque<>());
            }
            if (map.get(idx).size() == k) {
                l = Math.max(l, map.get(idx).removeFirst() + 1);
            }
            Deque<Integer> deque = map.get(idx);
            deque.addLast(r);
        }
        if (r - l > maxLen) {
            maxLen = r - l;
            start = l;
        }
        return s.substring(start, maxLen + start);
    }
}
