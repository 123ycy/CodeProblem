package normal;

/**
 * @ClassName: SearchInRotatedSortedArray
 * @Description: 33. 搜索旋转排序数组
 * @Author: zhaooo
 * @Date: 2024/01/22 19:53
 */
public class SearchInRotatedSortedArray {
    // lower bound
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) >> 1;
            // target在右侧
            if (target < nums[0]) {
                if (nums[mid] < nums[0] && nums[mid] >= target) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
                // 在左侧
            } else {
                if (nums[mid] >= nums[0] && nums[mid] < target) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
        }
        return nums[l] == target ? l : -1;
    }

    // 中途退出
    public int search2(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (nums[mid] == target) {
                return mid;
            }
            // target在右侧
            if (target < nums[0]) {
                if (nums[mid] < nums[0] && nums[mid] > target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
                // 在左侧
            } else {
                if (nums[mid] >= nums[0] && nums[mid] < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }
}
