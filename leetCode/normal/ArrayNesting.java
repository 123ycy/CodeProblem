package normal;

/**
 * @ClassName: ArrayNesting
 * @Description: 565. 数组嵌套
 * @Author: zhaooo
 * @Date: 2022/7/17/11:02
 */
public class ArrayNesting {
    /**
     * 类似并查集原理
     * @param nums
     * @return
     */
    public int arrayNesting(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            // 已经查找过就跳过
            if (nums[i] < i) {
                continue;
            }
            int count = 1;
            int cur = i;
            // 如果没到该集合的底
            while (nums[cur] != i) {
                int tmp = nums[cur];
                // 设置祖先为i，即路径压缩
                nums[cur] = i;
                cur = tmp;
                count++;
            }
            res = Math.max(res, count);
        }
        return res;
    }

    /**
     * 一次遍历
     * @param nums
     * @return
     */
    public int arrayNesting2(int[] nums) {
        int ans = 0, n = nums.length;
        for (int i = 0; i < n; ++i) {
            int cnt = 0;
            // 不同数字的个数和nums的长度相同，即没有重复数字，可以用i遍历所有
            while (nums[i] < n) {
                int num = nums[i];
                nums[i] = n;
                // 下一个nums[i]一定存在
                i = num;
                ++cnt;
            }
            ans = Math.max(ans, cnt);
        }
        return ans;
    }
}
