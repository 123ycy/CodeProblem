package normal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: RandomPickWithWeight
 * @Description: 528. 按权重随机选择
 * @Author: zhaooo
 * @Date: 2023/12/26 15:26
 */
public class RandomPickWithWeight {
    /**
     * 暴力 超出内存限制
     */
    static class Solution {
        List<Integer> list;

        public Solution(int[] w) {
            list = new ArrayList<>();
            for (int i = 0; i < w.length; i++) {
                for (int j = 0; j < w[i]; j++) {
                    list.add(i);
                }
            }
        }

        public int pickIndex() {
            return list.get(new Random(System.currentTimeMillis()).nextInt(list.size()));
        }
    }

    static class Solution2 {
        int[] arr;
        int sum = 0;
        Random random;

        public Solution2(int[] w) {
            arr = new int[w.length];
            random = new Random();
            for (int i = 0; i < w.length; i++) {
                arr[i] = sum;
                sum += w[i];
            }
        }

        public int pickIndex() {
            int x = random.nextInt(sum);
            int l = 0, r = arr.length - 1;
            // 二分查找 找到将要插入位置
            while (l <= r) {
                int mid = (l + r) >> 1;
                if (arr[mid] == x) {
                    return mid;
                }
                if (arr[mid] < x) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            // 将要插入位置的前一个即为答案
            return l - 1;
        }
    }
}
