package normal;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName: PartitionToKEqualSumSubsets
 * @Description: 698. 划分为k个相等的子集
 * @Author: zhaooo
 * @Date: 2022/9/20/9:39
 */
public class PartitionToKEqualSumSubsets {

    int[] nums;
    int per, n, k;
    boolean[] dp;
    Random random = new Random(System.currentTimeMillis());
    double hi = 1e9, lo = 1e-4, fa = 0.95;
    int MAX_SA_TIMES = 600;
    boolean ans;

    /**
     * 状态压缩 + 记忆化搜索
     * 相当于每次装满一个桶
     * @param nums
     * @param k
     * @return
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        this.nums = nums;
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) {
            return false;
        }
        per = sum / k;
        Arrays.sort(nums);
        n = nums.length;
        if (nums[n - 1] > per) {
            return false;
        }
        //
        dp = new boolean[1 << n];
        Arrays.fill(dp, true);
        // 第i位为1表示可用 故初始值为全1
        return dfs((1 << n) - 1, 0);
    }

    public boolean dfs(int s, int cur) {
        // 为0表示每一位都使用
        if (s == 0) {
            return true;
        }
        // 已经出现过的情况返回false
        if (!dp[s]) {
            return false;
        }
        // 记住当前情况
        dp[s] = false;
        // 取一个数访问
        for (int i = 0; i < n; i++) {
            if (nums[i] + cur > per) {
                break;
            }
            if (((s >> i) & 1) != 0) {
                // (cur + nums[i]) % per 表示装满当前的再装下一个
                // 每次dfs都是新的s 只有dp[s]有记忆 相当于不用回溯
                if (dfs(s ^ (1 << i), (cur + nums[i]) % per)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 状态压缩 + 动态规划
     * 与上面记忆化搜索相似
     * @param nums
     * @param k
     * @return
     */
    public boolean canPartitionKSubsets2(int[] nums, int k) {
        int all = Arrays.stream(nums).sum();
        if (all % k != 0) {
            return false;
        }
        int per = all / k;
        Arrays.sort(nums);
        int n = nums.length;
        if (nums[n - 1] > per) {
            return false;
        }
        // dp[S]来表示在可用的数字状态为S的情况下是否可能可行，初始全部状态为记录为不可行状态False，只记dp[0]=True为可行状态
        boolean[] dp = new boolean[1 << n];
        // 每个状态对应的和
        int[] curSum = new int[1 << n];
        dp[0] = true;
        for (int i = 0; i < 1 << n; i++) {
            // 该状态不是由前面状态推出来的
            if (!dp[i]) {
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (curSum[i] + nums[j] > per) {
                    break;
                }
                // 当前位还未使用
                if (((i >> j) & 1) == 0) {
                    // next为使用该num后的状态
                    int next = i | (1 << j);
                    if (!dp[next]) {
                        curSum[next] = (curSum[i] + nums[j]) % per;
                        // 推出该状态
                        dp[next] = true;
                    }
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    public boolean canPartitionKSubsets3(int[] nums, int k) {
        Arrays.sort(nums);
        int sum = Arrays.stream(nums).sum();
        int per = sum / k;
        if (sum % k != 0 || nums[nums.length - 1] > per) {
            return false;
        }
        int[] bucks = new int[k];
        // 第一个元素放在哪里都一样，直接放在第一个桶
        bucks[0] += nums[nums.length - 1];
        return dfs(nums, bucks, nums.length - 2, per);
    }

    boolean dfs(int[] nums, int[] bucks, int i, int max) {
        if (i < 0) {
            return true;
        }
        for (int j = 0; j < bucks.length; j++) {
            // 如果当前桶的值和前一个桶一样则剪枝跳过
            if (j > 0 && bucks[j - 1] == bucks[j]) {
                continue;
            }
            if (bucks[j] + nums[i] <= max) {
                bucks[j] += nums[i];
                if (dfs(nums, bucks, i - 1, max)) {
                    return true;
                }
                // 回溯
                bucks[j] -= nums[i];
            }
        }
        return false;
    }

    /**
     * 模拟退火
     * 因为将n个数划分为k份，等效于用n个数构造出一个「特定排列」，然后对「特定排列」进行固定模式的构造逻辑，
     * 就能实现「答案」与「目标排列」的对应关系。（类似与loss损失函数）基于此，我们可以使用「模拟退火」进行求解。
     * https://leetcode.cn/problems/partition-to-k-equal-sum-subsets/solution/by-ac_oier-mryw/
     * https://www.cnblogs.com/Daidly/p/16597843.html
     * @param _nums
     * @param _k
     * @return
     */
    public boolean canPartitionKSubsets4(int[] _nums, int _k) {
        nums = _nums;
        k = _k;
        int tot = 0;
        for (int x : nums) {
            tot += x;
        }
        if (tot % k != 0) {
            return false;
        }
        n = nums.length;
        per = tot / k;
        while (!ans && MAX_SA_TIMES-- > 0) {
            sa();
        }
        return ans;
    }


    // 损失函数
    int calc() {
        int diff = per * k;
        // 填满k个桶
        for (int i = 0, j = 0; i < n && j < k; j++) {
            int cur = 0;
            // 按顺序（该顺序已经shuffle）添加
            while (i < n && cur + nums[i] <= per) {
                cur += nums[i++];
            }
            // 添加到不能再添加就减去该值
            diff -= cur;
        }
        if (diff == 0) {
            ans = true;
        }
        return diff;
    }

    // 模拟退火过程
    void sa() {
        // 先shuffle即从随机状态降温
        shuffle(nums);
        // fa为温度变化率t为当前温度且t>0，t*=fa模拟降温过程
        for (double t = hi; t > lo && !ans; t *= fa) {
            // 从n内取2个坐标用于生成随机数x·
            int a = random.nextInt(n), b = random.nextInt(n);
            if (a == b) {
                continue;
            }
            // 旧f(x)
            int prev = calc();
            swap(nums, a, b);
            // 随机出的新f(x·)
            int cur = calc();
            int diff = cur - prev;

            // 再次swap(nums, a, b)表示不接受新解

            // 如果diff>=0 即 当前情况非局部最优解 以一定的概率接受该解即不swap(nums, a, b)
            // 因为p = exp(-1 * (abs(diff) / t)为定值 故生成一个0到1的随机数与其比较
            // diff越大p越小，t越小p越小，接受的概率越小
            if (diff >= 0 && Math.exp(-1 * (Math.abs(diff) / t)) < random.nextDouble()) {
                swap(nums, a, b);
            }

            // 也可以写为：
            // diff小于0时 log(diff/t)一定下小于0 不swap(nums, a, b)
            // diff大于0时 diff越大和t越小时log(diff/t)越大 越不接受该解
            // if (Math.log(diff / t) > random.nextDouble()) swap(nums, a, b);
        }
    }

    void shuffle(int[] nums) {
        for (int i = n; i > 0; i--) {
            swap(nums, random.nextInt(i), i - 1);
        }
    }

    void swap(int[] nums, int a, int b) {
        int c = nums[a];
        nums[a] = nums[b];
        nums[b] = c;
    }
}
