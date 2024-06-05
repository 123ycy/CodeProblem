package normal;

/**
 * @ClassName: SubarrayProductLessThanK
 * @Description: 713. 乘积小于 K 的子数组
 * @Author: zhaooo
 * @Date: 2023/09/30 12:56
 */
public class SubarrayProductLessThanK {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1)
            return 0;
        int n = nums.length, ans = 0, prod = 1, left = 0;
        for (int right = 0; right < n; ++right) {
            prod *= nums[right];
            while (prod >= k) // 不满足要求
                prod /= nums[left++];
            // 对于有n个元素的集合：
            //
            // 包含 1 个元素的子集有 n 个
            // 包含 2 个连续元素的子集有 n - 1 个
            // ...
            // 包含 n 个连续元素的子集有 1 个
            // 总共有 1 + 2 + 3 +... + n = （n+1）*n/2个连续元素的子集
            // 那么对于n-1个元素的集合：共有(n) * (n-1)/2个连续元素的子集。
            //
            // (n+1)n/2 - n(n-1)/2 = n/2 * (2)=n 对于n-1个元素的集合，增加第n个元素，则增加的连续元素子集数为n个。
            //
            // 对于a[l] ... a[r - 1] 的集合，增加第r个元素a[r]，则增加的连续元素子集数为 a[l] ... a[r] 范围内的元素总数即 r - l +1个。
            ans += right - left + 1;
        }
        return ans;
    }
}
