package normal;

/**
 * @ClassName: Candy
 * @Description: 135. 分发糖果
 * https://leetcode.cn/problems/candy/solutions/533150/fen-fa-tang-guo-by-leetcode-solution-f01p/
 * @Author: zhaooo
 * @Date: 2023/7/5/22:40
 */
public class Candy {
    public int candy(int[] ratings) {
        int len = ratings.length, sum = 0;
        int[] l = new int[len], r = new int[len];
        l[0] = 1;
        r[len - 1] = 1;
        for (int i = 1; i < len; i++) {
            if (ratings[i] > ratings[i - 1]) {
                l[i] = l[i - 1] + 1;
            } else {
                l[i] = 1;
            }
        }
        for (int i = len - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                r[i] = r[i + 1] + 1;
            } else {
                r[i] = 1;
            }
        }
        for (int i = 0; i < len; i++) {
            sum += Math.max(l[i], r[i]);
        }
        return sum;
    }

    public int candy2(int[] ratings) {
        int n = ratings.length;
        int ret = 1;
        int inc = 1, dec = 0, pre = 1;
        for (int i = 1; i < n; i++) {
            if (ratings[i] >= ratings[i - 1]) {
                dec = 0;
                pre = ratings[i] == ratings[i - 1] ? 1 : pre + 1;
                ret += pre;
                inc = pre;
            } else {
                dec++;
                if (dec == inc) {
                    dec++;
                }
                ret += dec;
                pre = 1;
            }
        }
        return ret;
    }
}
