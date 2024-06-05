package normal;

import java.util.*;

/**
 * @ClassName: RandomPickWithBlacklist
 * @Description: 710. 黑名单中的随机数
 * @Author: zhaooo
 * @Date: 2022/6/26/10:51
 */
public class RandomPickWithBlacklist {
    /**
     * 爆内存
     */
    class Solution {

        int[] l;
        int len;
        Random random;

        public Solution(int n, int[] blacklist) {
            len = n - blacklist.length;
            l = new int[len];
            random = new Random();
            Arrays.sort(blacklist);
            for (int i = 0, j = 0, k = 0; i < len; i++) {
                if (k < blacklist.length && j == blacklist[k]) {
                    while (k < blacklist.length && j == blacklist[k]) {
                        while (j == blacklist[k]) {
                            j++;
                        }
                        k++;
                    }
                }
                l[i] = j++;
            }
        }

        public int pick() {
            return l[random.nextInt(len)];
        }
    }

    /**
     * 哈希映射
     */
    class Solution2 {
        Map<Integer, Integer> b2w;
        Random random;
        int bound;

        public Solution2(int n, int[] blacklist) {
            b2w = new HashMap<Integer, Integer>();
            random = new Random();
            int m = blacklist.length;
            bound = n - m;
            Set<Integer> black = new HashSet<Integer>();
            for (int b : blacklist) {
                if (b >= bound) {
                    black.add(b);
                }
            }

            int w = bound;
            for (int b : blacklist) {
                if (b < bound) {
                    while (black.contains(w)) {
                        ++w;
                    }
                    b2w.put(b, w);
                    ++w;
                }
            }
        }

        public int pick() {
            int x = random.nextInt(bound);
            return b2w.getOrDefault(x, x);
        }
    }

}
