package normal;

/**
 * @ClassName: JumpGameII
 * @Description: 45. 跳跃游戏 II
 * @Author: zhaooo
 * @Date: 2023/3/14/15:39
 */
public class JumpGameII {
    /**
     * 贪心+双指针
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        // [l,r]表示已经确定最小跳跃次数的区间
        // step表示当前区间最小跳跃次数
        int len = nums.length, l = 0, r = 0, step = 0;
        int[] dp = new int[len];
        while (r < len - 1) {
            // max记录从当前区间能一步能到的最大位置
            int max = r;
            // 每次确定一个区间的最小跳跃次数
            for (int i = l; i <= r; i++) {
                max = Math.max(i + nums[i], max);
            }
            max = Math.min(len - 1, max);
            l = r + 1;
            r = max;
            step++;
        }
        return step;
    }
}
