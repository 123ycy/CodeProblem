package normal;

import java.util.Random;

/**
 * @ClassName: RandomPointInNonOverlappingRectangles
 * @Description: 497. 非重叠矩形中的随机点
 * @Author: zhaooo
 * @Date: 2022/6/9/17:17
 */
public class RandomPointInNonOverlappingRectangles {
    /**
     * 前缀和+二分查找
     */
    class Solution {
        int[] sums;
        int[][] rects;
        int allSum, len;
        Random random;

        public Solution(int[][] rects) {
            len = rects.length;
            this.rects = rects;
            sums = new int[len + 1];
            for (int i = 0; i < len; i++) {
                sums[i + 1] = sums[i] + sum(rects[i]);
            }
            allSum = sums[len];
            random = new Random();
        }

        public int[] pick() {
            // nextInt的范围是[0,allSum)
            int randomSum = random.nextInt(allSum) + 1;
            //范围是[l,r]
            int l = 1, r = len;
            // 退出时l=r,表示只有一个元素可选
            while (l < r) {
                int mid = (l + r) >> 1;
                if (sums[mid] < randomSum) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
            int[] rect = rects[l - 1];
            int x = random.nextInt(rect[2] - rect[0] + 1) + rect[0];
            int y = random.nextInt(rect[3] - rect[1] + 1) + rect[1];
            return new int[]{x, y};
        }

        public int sum(int[] rect) {
            return (rect[2] - rect[0] + 1) * (rect[3] - rect[1] + 1);
        }
    }
}
