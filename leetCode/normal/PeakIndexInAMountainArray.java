package normal;

/**
 * @ClassName: PeakIndexInAMountainArray
 * @Description: 852. 山脉数组的峰顶索引
 * @Author: zhaooo
 * @Date: 2023/12/26 14:30
 */
public class PeakIndexInAMountainArray {
    /**
     * 二分
     * @param arr
     * @return
     */
    public int peakIndexInMountainArray(int[] arr) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = (r + l) >> 1;
            if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) {
                return mid;
            }
            if (arr[mid + 1] > arr[mid]) {
                l = mid + 1;
            } else if (arr[mid - 1] > arr[mid]) {
                r = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 三分
     * https://leetcode.cn/problems/peak-index-in-a-mountain-array/solutions/828434/gong-shui-san-xie-er-fen-san-fen-cha-zhi-5gfv
     * @param arr
     * @return
     */
    public int peakIndexInMountainArray2(int[] arr) {
        int n = arr.length;
        int l = 1, r = n - 2;
        while (l < r) {
            int m1 = l + (r - l) / 3, m2 = r - (r - l) / 3;
            if (arr[m1] > arr[m1 - 1] && arr[m1] > arr[m1 + 1]) {
                return m1;
            }
            if (arr[m2] > arr[m2 - 1] && arr[m2] > arr[m2 + 1]) {
                return m2;
            }
            if (arr[m1] > arr[m1 - 1]) {
                l = m1;
            }
            if (arr[m2] > arr[m2 + 1]) {
                r = m2;
            }
        }
        return l;
    }
}
