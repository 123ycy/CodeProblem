package normal;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: BulbSwitcherII
 * @Description: 672. 灯泡开关 Ⅱ
 * @Author: zhaooo
 * @Date: 2022/9/15/10:24
 */
public class BulbSwitcherII {
    Set<Integer> set = new HashSet<>();

    /**
     * 暴力枚举 超时
     * @param n
     * @param presses
     * @return
     */
    public int flipLights(int n, int presses) {
        if (n == 1) {
            return presses == 0 ? 1 : 2;
        }
        if (n == 2) {
            return presses == 0 ? 1 : presses == 1 ? 3 : 4;
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i <= presses; i++) {
            for (int j = i; j <= presses; j++) {
                for (int k = j; k <= presses; k++) {
                    int l_ = (presses - i - j - k) & 1;
                    int i_ = i & 1;
                    int j_ = j & 1;
                    int k_ = k & 1;
                    if (i_ + j_ + k_ == 3) {
                        i_ = 0;
                        j_ = 0;
                        k_ = 0;
                    }
                    set.add((i_ << 3) + (j_ << 2) + (k_ << 1) + l_);
                }
            }
        }
        return set.size();
    }

    /**
     * 数学
     * https://leetcode.cn/problems/bulb-switcher-ii/solution/dengp-by-capital-worker-51rb/
     * @param n
     * @param presses
     * @return
     */
    public int flipLights2(int n, int presses) {
        if (presses == 0) {
            return 1;
        }
        // 以下presses大于0
        if (n == 1) {
            return 2;
        } else if (n == 2) {
            return presses == 1 ? 3 : 4;
        } else {
            return presses == 1 ? 4 : presses == 2 ? 7 : 8;
        }
    }
}
