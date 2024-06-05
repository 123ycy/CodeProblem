package normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MaximumProductOfWordLengths
 * @Description: 318. 最大单词长度乘积
 * @Author: zhaooo
 * @Date: 2023/09/23 13:33
 */
public class MaximumProductOfWordLengths {
    /**
     * 暴力
     *
     * @param words
     * @return
     */
    public int maxProduct(String[] words) {
        int ans = 0;
        for (int i = 0; i < words.length; i++) {
            int cur = toMask(words[i]);
            for (int j = i + 1; j < words.length; j++) {
                if ((toMask(words[j]) & cur) == 0) {
                    ans = Math.max(ans, words[i].length() * words[j].length());
                }
            }
        }
        return ans;
    }

    int toMask(String word) {
        int ans = 0;
        for (int i = 0; i < word.length(); i++) {
            ans |= (1 << (word.charAt(i) - 'a'));
        }
        return ans;
    }

    /**
     * 保存相同的mask的较长值
     *
     * @param words
     * @return
     */
    public int maxProduct2(String[] words) {
        Map<Integer, Integer> map = new HashMap<>();
        for (String w : words) {
            int t = 0, m = w.length();
            for (int i = 0; i < m; i++) {
                int u = w.charAt(i) - 'a';
                t |= (1 << u);
            }
            if (!map.containsKey(t) || map.get(t) < m) {
                map.put(t, m);
            }
        }
        int ans = 0;
        for (int a : map.keySet()) {
            for (int b : map.keySet()) {
                if ((a & b) == 0) ans = Math.max(ans, map.get(a) * map.get(b));
            }
        }
        return ans;
    }
}
