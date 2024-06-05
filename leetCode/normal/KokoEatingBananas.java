package normal;

/**
 * @ClassName: normal.KokoEatingBananas
 * @Description: 875. 爱吃香蕉的珂珂
 * @Author: zhaooo
 * @Date: 2022/6/7/10:15
 */
public class KokoEatingBananas {
    class Solution {
        /**
         * 二分查找
         * @param piles
         * @param h
         * @return
         */
        public int minEatingSpeed(int[] piles, int h) {
            int low = 1;
            int high = 0;
            for (int pile : piles) {
                high = Math.max(high, pile);
            }
            int k = high;
            while (low <= high) {
                int speed = (high + low) >> 1;
                long time = getTime(piles, speed);
                if (time <= h) {
                    k = speed;
                    high = speed - 1;
                } else {
                    low = speed + 1;
                }
            }
            return k;
        }

        public long getTime(int[] piles, int speed) {
            long time = 0;
            for (int pile : piles) {
                int curTime = (pile + speed - 1) / speed;
                time += curTime;
            }
            return time;
        }
    }
}
