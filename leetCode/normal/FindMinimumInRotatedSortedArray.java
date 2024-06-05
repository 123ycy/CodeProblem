package normal;

/**
 * @ClassName: FindMinimumInRotatedSortedArray
 * @Description: 153. 寻找旋转排序数组中的最小值
 * https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/solutions/126635/er-fen-cha-zhao-wei-shi-yao-zuo-you-bu-dui-cheng-z/?envType=study-plan-v2&envId=top-interview-150
 * @Author: zhaooo
 * @Date: 2023/08/19 13:11
 */
public class FindMinimumInRotatedSortedArray {
    /**
     * 二分
     * 左、中、右三个位置的值相比较，有以下几种情况：
     *
     * 左值 < 中值, 中值 < 右值 ：没有旋转，最小值在最左边，可以收缩右边界
     *
     *         右
     *      中
     *  左
     * 左值 > 中值, 中值 < 右值 ：有旋转，最小值在左半边，可以收缩右边界
     *
     *  左
     *          右
     *      中
     * 左值 < 中值, 中值 > 右值 ：有旋转，最小值在右半边，可以收缩左边界
     *
     *      中
     *  左
     *          右
     * 左值 > 中值, 中值 > 右值 ：单调递减，不可能出现
     *
     *  左
     *     中
     *         右
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        // mid = left + (right - left) / 2 这里整数除法是向下取整的地板除，mid更靠近left， 再结合while循环的条件left < right
        // 可以知道left <= mid，mid < right， 即在while循环内，mid始终小于right。
        // 因此在while循环内，nums[mid]要么大于要么小于nums[right]，不会等于
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 左右不对称的原因是： 这是循环前升序排列的数，左边的数小，右边的数大，而且我们要找的是最小值，肯定是偏向左找，所以左右不对称了。
            // 寻找最大值可以和nums[left]比较
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }
}