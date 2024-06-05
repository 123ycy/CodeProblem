package normal;

/**
 * @ClassName: FindPeakElement
 * @Description: 162. 寻找峰值
 * @Author: zhaooo
 * @Date: 2023/3/19/12:49
 */
public class FindPeakElement {
    /**
     * 二分查找+递归
     * 考虑单调增加的极端情况，峰值一定在较大元素的那一部分
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return 0;
        }
        if (len == 2) {
            return nums[0] > nums[1] ? 0 : 1;
        }
        return dfs(nums, 0, len - 1);
    }

    // [l,r]范围内的二分查找 l,r 属于 [0,len-1];
    int dfs(int[] nums, int l, int r) {
        if (l > r) {
            return -1;
        }
        int mid = (l + r) >> 1;
        // mid == 0时 l一定为0,r一定为1
        if (mid == 0) {
            return nums[0] > nums[1] ? 0 : 1;
        }
        // 当mid = len - 1时一定有 l==r 因为mid是右半部分的的一个
        if (l == r) {
            return l;
        }
        if (nums[mid - 1] < nums[mid] && nums[mid + 1] < nums[mid]) {
            return mid;
        }
        return nums[mid + 1] > nums[mid] ? dfs(nums, mid + 1, r) : dfs(nums, l, mid - 1);
    }

    /**
     * 二分+迭代
     * @param nums
     * @return
     */
    public int findPeakElement2(int[] nums) {
        int l = 0, r = nums.length - 1;
        // [l,r]范围内的二分查找 l,r 属于 [0,len-1];
        while (l < r) {
            int mid = (l + r) >> 1;
            // r不改为mid+1 因为有可能结果是mid
            if (nums[mid] > nums[mid + 1]) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        // 当l==r时区间只有一个元素
        return l;
    }
}
