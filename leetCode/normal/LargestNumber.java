package normal;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @ClassName: LargestNumber
 * @Description: 179. 最大数
 * https://leetcode.cn/problems/largest-number/solutions/715680/zui-da-shu-by-leetcode-solution-sid5/?orderBy=most_votes
 * @Author: zhaooo
 * @Date: 2023/04/02 12:19
 */
public class LargestNumber {
    // 比较+自定义排序
    public String largestNumber(int[] nums) {
        int n = nums.length;
        // 转换成包装类型，以便传入 Comparator<T> 对象
        Integer[] numsArr = new Integer[n];
        for (int i = 0; i < n; i++) {
            numsArr[i] = nums[i];
        }
        Arrays.sort(numsArr, (x, y) -> {
            long sx = 10, sy = 10;
            // 找到最接近的10^n
            while (sx <= x) {
                sx *= 10;
            }
            while (sy <= y) {
                sy *= 10;
            }
            // 如果x:y > y:x 表示x应该在y前面 :表示连接
            // 无论x和y之间有那些数 x...y > y...x 等价于x:y > y:x
            // 不改变顺序时返回负数
            return (int) (-sy * x - y + sx * y + x);
        });
        // 0在最后，如果首位为0表示全0
        if (numsArr[0] == 0) {
            return "0";
        }
        StringBuilder ret = new StringBuilder();
        for (int num : numsArr) {
            ret.append(num);
        }
        return ret.toString();
    }
}
