package normal;

/**
 * @ClassName: FindFirstAndLastPositionOfElementInSortedArray
 * @Description: 34. 在排序数组中查找元素的第一个和最后一个位置
 * @Author: zhaooo
 * @Date: 2023/12/24 16:16
 */
public class FindFirstAndLastPositionOfElementInSortedArray {
    /**
     * lowerBound
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int lower = lowerBound(nums, target);
        // lower的范围是[0,len] upper的范围是[-1,len-1]
        if (lower == nums.length || nums[lower] != target) {
            return new int[]{-1, -1};
        }
        // lower是要左边界或者要插入的位置
        // lower(target+1)-1 就是上边界
        int upper = lowerBound(nums, target + 1);
        return new int[]{lower, upper - 1};
    }

    public int lowerBound(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }
    /**
     * 二分 + 循环不变量
     * 参考 commonDS/BinarySearch.java
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange2(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false);
        if (leftIdx < nums.length && rightIdx >= 0 && nums[leftIdx] == target) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }

    // 利用循环不变量分为两个二分查找
    // searchL为true时：[ < target] [l, r] [ >= target]
    // searchL为false时：[ <= target] [l, r] [ > target]
    // 由循环不变量 -> 循环条件/退出条件：[l, r]的长度为0，即[r, l]此时l = r + 1
    // 由循环不变量 -> 循环体：略
    // 由循环不变量 -> 返回值：
    //  searchL为true时：返回[ >= target]的第一个数的下标，即l，因为此时l = r + 1，且r的右侧就是[ >= target]
    //  searchL为false时：返回[ <= target]的最后一个数的下标，即r，因为此时r = l - 1，且l的左侧就是[ <= target]
    // 故返回值是target的位置或者将要插入的位置
    public int binarySearch(int[] nums, int target, boolean searchL) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (right + left) / 2;
            if (nums[mid] == target) {
                left = searchL ? left : mid + 1;
                right = searchL ? mid - 1 : right;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return searchL ? left : right;
    }
}
