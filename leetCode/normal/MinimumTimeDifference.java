package normal;

import java.util.List;

/**
 * @ClassName: MinimumTimeDifference
 * @Description: 539. 最小时间差
 * @Author: zhaooo
 * @Date: 2023/11/09 09:40
 */
public class MinimumTimeDifference {
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        if (n > 1440) {
            return 0;
        }
        boolean[] times = new boolean[1440];
        for (String s : timePoints) {
            int h = Integer.parseInt(s.substring(0, 2));
            int m = Integer.parseInt(s.substring(3, 5));
            if (times[h * 60 + m]) {
                return 0;
            }
            times[h * 60 + m] = true;
        }
        int pre = -1440, res = 1440, last = -1;
        for (int i = 0; i < times.length; i++) {
            if (times[i]) {
                res = Math.min(res, i - pre);
                pre = i;
                last = i;
            }
        }
        // 首尾时间差
        for (int i = 1; i < res; i++) {
            if (times[(last + i) % 1440]) {
                return i;
            }
        }
        return res;
    }
}
