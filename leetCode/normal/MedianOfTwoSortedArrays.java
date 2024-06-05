package normal;

/**
 * @ClassName: MedianOfTwoSortedArrays
 * @Description: 4. 寻找两个正序数组的中位数
 * @Author: zhaooo
 * @Date: 2024/02/22 11:24
 */
public class MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        if (len % 2 == 1) {
            int midIndex = len / 2;
            return getKthElement(nums1, nums2, midIndex + 1);
        } else {
            int midIndex1 = len / 2 - 1, midIndex2 = len / 2;
            return (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
        }
    }

    // 利用左闭右开
    public int getKthElement(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length, len2 = nums2.length;
        // [idx, len)是可用范围 左闭右开便于计算长度和切分空间
        int idx1 = 0, idx2 = 0;
        while (true) {
            // 边界情况
            // nums1全部排除
            if (idx1 == len1) {
                return nums2[idx2 + k - 1];
            }
            // nums2全部排除
            if (idx2 == len2) {
                return nums1[idx1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[idx1], nums2[idx2]);
            }

            // 正常情况 idx是左侧边界
            int newIdx1 = Math.min(idx1 + k / 2, len1);
            int newIdx2 = Math.min(idx2 + k / 2, len2);
            int pivot1 = nums1[newIdx1 - 1], pivot2 = nums2[newIdx2 - 1];
            //        pivot
            // [idx, newIdx)[newIdx, len)
            // [    k/2    ][ len - k/2 ]
            // 删除[idx1, newIdx1)
            if (pivot1 < pivot2) {
                k -= (newIdx1 - idx1);
                idx1 = newIdx1;
            } else {
                k -= (newIdx2 - idx2);
                idx2 = newIdx2;
            }
        }
    }
}
