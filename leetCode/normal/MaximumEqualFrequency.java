package normal;

/**
 * @ClassName: MaximumEqualFrequency
 * @Description: 1224. 最大相等频率
 * @Author: zhaooo
 * @Date: 2022/8/19/12:59
 */
public class MaximumEqualFrequency {
    final int N = 100010;

    public int maxEqualFreq(int[] nums) {
        int[] cnt = new int[N], sum = new int[N];
        int max = 0, ans = 0;
        for (int i = 0; i < nums.length; i++) {
            sum[cnt[nums[i]]++]--;
            sum[cnt[nums[i]]]++;
            max = Math.max(cnt[nums[i]], max);
            // 三种更新最长前缀的情况
            // 1. 最大出现次数为 1
            if (max == 1) {
                ans = i + 1;
            }
            // 2. 除了一个数字出现一次，剩下数字均出现 max 次
            if (max * sum[max] + 1 == i + 1) {
                ans = i + 1;
            }
            // 3. 除了一个数字出现 max 次，剩下数字均出现 max - 1 次
            if ((max - 1) * sum[max - 1] + max == i + 1) {
                ans = i + 1;
            }
        }
        return ans;
    }
}
