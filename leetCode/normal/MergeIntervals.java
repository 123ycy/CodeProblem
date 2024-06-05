package normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: MergeIntervals
 * @Description: 56. 合并区间
 * @Author: zhaooo
 * @Date: 2023/12/27 16:13
 */
public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        List<int[]> list = new ArrayList<>();
        int r = intervals[0][1];
        list.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > r) {
                r = intervals[i][1];
                list.add(intervals[i]);
            } else {
                if (intervals[i][1] > r) {
                    r = intervals[i][1];
                    list.get(list.size() - 1)[1] = intervals[i][1];
                }
            }
        }
        int[][] ans = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }
}


