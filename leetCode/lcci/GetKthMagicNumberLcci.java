package lcci;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * @ClassName: GetKthMagicNumberLcci
 * @Description: 面试题 17.09. 第 k 个数
 * @Author: zhaooo
 * @Date: 2022/9/28/11:00
 */
public class GetKthMagicNumberLcci {
    /**
     * 优先队列
     * @param k
     * @return
     */
    public int getKthMagicNumber(int k) {
        long ans = 1;
        int[] nums = new int[]{3, 5, 7};
        Set<Long> set = new HashSet<>();
        Queue<Long> pq = new PriorityQueue<>();
        pq.add(1L);
        set.add(1L);
        for (int i = 0; i < k; i++) {
            ans = pq.remove();
            for (int num : nums) {
                long tmp = ans * num;
                if (!set.contains(tmp)) {
                    set.add(tmp);
                    pq.add(tmp);
                }
            }
        }
        return (int) ans;
    }

    /**
     * 多路并归
     * https://leetcode.cn/problems/get-kth-magic-number-lcci/solution/by-ac_oier-2czm/
     * @param k
     * @return
     */
    public int getKthMagicNumber2(int k) {
        int[] ans = new int[k + 1];
        ans[1] = 1;
        for (int i3 = 1, i5 = 1, i7 = 1, idx = 2; idx <= k; idx++) {
            // a是[1,3,9,...] 同理b c
            int a = ans[i3] * 3, b = ans[i5] * 5, c = ans[i7] * 7;
            // 每次前进最小的一个
            int min = Math.min(a, Math.min(b, c));
            if (min == a) {
                i3++;
            }
            if (min == b) {
                i5++;
            }
            if (min == c) {
                i7++;
            }
            ans[idx] = min;
        }
        return ans[k];
    }
}
