package normal;

import java.util.*;

/**
 * @ClassName: PerfectRectangle
 * @Description: 391. 完美矩形 
 * @Author: zhaooo
 * @Date: 2022/9/17/11:55
 */
public class PerfectRectangle {
    /**
     * 扫描线+离散化+数组维护
     * @param rectangles
     * @return
     */
    public boolean isRectangleCover(int[][] rectangles) {
        int len = rectangles.length, lIdx = 0;
        // y去重并排序
        Set<Integer> ySet = new TreeSet<>();
        int[][] lines = new int[len << 1][4];
        for (int[] rectangle : rectangles) {
            lines[lIdx++] = new int[]{rectangle[0], rectangle[1], rectangle[3], 1};
            lines[lIdx++] = new int[]{rectangle[2], rectangle[1], rectangle[3], -1};
            ySet.add(rectangle[1]);
            ySet.add(rectangle[3]);
        }
        List<Integer> yList = new ArrayList<>(ySet);
        // 离散化坐标映射
        Map<Integer, Integer> y2Index = new HashMap<>();
        for (int i = 0; i < yList.size(); i++) {
            y2Index.put(yList.get(i), i);
        }
        Arrays.sort(lines, Comparator.comparingInt(l -> l[0]));
        // y的离散化坐标为对应在segments中的下标
        int[] segments = new int[yList.size() - 1];
        for (int i = 0; ; ) {
            int curX = lines[i][0];
            int j = i + 1;
            // [i, j)为相同x坐标的所有线
            while (j != lines.length && lines[j][0] == curX) {
                j++;
            }
            for (; i < j; i++) {
                // lines[] {x下标, y的left, y的right, 入边还是出边}
                int left = y2Index.get(lines[i][1]), right = y2Index.get(lines[i][2]);
                // 改变[left, right]范围内的segments
                for (int k = left; k < right; k++) {
                    segments[k] += lines[i][3];
                }
            }
            if (i == lines.length) {
                break;
            }
            // curX的所有扫描后 若无重叠则segments[k]均为1
            for (int seg : segments) {
                if (seg != 1) {
                    return false;
                }
            }
            i = j;
        }
        return true;
    }

    /**
     * 如果是完美矩形 那么一定满足两点：
     * （1）最左下 最左上 最右下 最右上 的四个点只出现一次 其他点成对出现
     * （2）四个点围城的矩形面积 = 小矩形的面积之和
     * https://leetcode.cn/problems/perfect-rectangle/solution/tong-ge-lai-shua-ti-la-zhao-gui-lu-ji-ba-oszq/
     * @param rectangles
     * @return
     */
    public boolean isRectangleCover2(int[][] rectangles) {
        // 计算每个小矩形面积是否等于大矩形面积
        // 看每个顶点出现的次数，如果最后出现一次的顶点不是四个，则说明不符合完美矩形
        int area = 0;
        Set<Integer> set = new HashSet<>();
        // 记录大矩形的左下角和右上角
        int llX = Integer.MAX_VALUE, llY = Integer.MAX_VALUE;
        int rtX = Integer.MIN_VALUE, rtY = Integer.MIN_VALUE;
        for (int[] rec : rectangles) {
            // 小矩形的坐标
            int x1 = rec[0];
            int y1 = rec[1];
            int x2 = rec[2];
            int y2 = rec[3];
            // 计算左下角
            if (x1 < llX || y1 < llY) {
                llX = x1;
                llY = y1;
            }
            // 计算右上角
            if (x2 > rtX || y2 > rtY) {
                rtX = x2;
                rtY = y2;
            }
            // 计算面积
            area += (x2 - x1) * (y2 - y1);
            // 记录每个顶点出现的次数
            record(set, x1, y1);
            record(set, x1, y2);
            record(set, x2, y1);
            record(set, x2, y2);
        }
        // 通过左下角和右上角坐标可以算出总面积
        int totalArea = (rtX - llX) * (rtY - llY);
        // 如果两个面积不相等，直接返回false
        if (area != totalArea) {
            return false;
        }
        // 四个为1的顶点正好是大矩形的四个顶点
        return set.size() == 4 && set.contains(key(llX, llY)) && set.contains(key(llX, rtY))
                && set.contains(key(rtX, llY)) && set.contains(key(rtX, rtY));
    }

    private void record(Set<Integer> set, int x, int y) {
        // 记录顶点出现的次数，如果一个顶点出现偶数次，则移除
        int key = key(x, y);
        if (set.contains(key)) {
            set.remove(key);
        } else {
            set.add(key);
        }
    }

    private int key(int x, int y) {
        // 二维坐标转一维，方便比较
        // 100000007是随便取的一个大质数
        // 这里即使溢出了也没什么问题
        return x * 100000007 + y;
    }
}
