package normal;

import java.util.Arrays;

/**
 * @ClassName: Heaters
 * @Description: 475. 供暖器
 * @Author: zhaooo
 * @Date: 2023/3/20/9:25
 */
public class Heaters {
    /**
     * 每次二分查找
     * @param houses
     * @param heaters
     * @return
     */
    public int findRadius(int[] houses, int[] heaters) {
        int ans = 0;
        Arrays.sort(heaters);
        for (int house : houses) {
            // i是比house左侧第一个元素
            int i = binarySearch(heaters, house);
            int j = i + 1;
            int leftDistance = i < 0 ? Integer.MAX_VALUE : house - heaters[i];
            int rightDistance = j >= heaters.length ? Integer.MAX_VALUE : heaters[j] - house;
            int curDistance = Math.min(leftDistance, rightDistance);
            ans = Math.max(ans, curDistance);
        }
        return ans;
    }

    public int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        if (nums[left] > target) {
            return -1;
        }
        // 在[l,r]内查找
        while (left < right) {
            // (l+r)/2向上取整
            int mid = (right - left + 1) / 2 + left;
            if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // left<=target
                left = mid;
            }
        }
        // 此时nums[left]<=target
        return left;
    }

    /**
     * 双指针+预处理两侧
     * @param houses
     * @param heaters
     * @return
     */
    public int findRadius2(int[] houses, int[] heaters) {
        int hLen = heaters.length;
        int[] h = new int[hLen + 2];
        Arrays.sort(houses);
        Arrays.sort(heaters);
        System.arraycopy(heaters, 0, h, 1, hLen);
        // 1 <= houses[i], heaters[i] <= 1e9
        h[0] = (int) (Integer.MIN_VALUE + 1e9);
        h[hLen + 1] = Integer.MAX_VALUE;
        int ret = 0, l = 0, r = 0;
        // 初始化l,r
        // heaters[r] >= house的第一个r和 r的前一个即为l
        // 即houses[i]在[l,r]中
        while (h[r] < houses[0]) {
            l = r;
            r++;
        }
        for (int house : houses) {
            // 每次找到该house的左右h
            if (house > h[r]) {
                while (h[r] < house) {
                    l = r;
                    r++;
                }
            }
            ret = Math.max(ret, Math.min(house - h[l], h[r] - house));
        }
        return ret;
    }
}
