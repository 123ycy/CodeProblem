package normal;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName: WiggleSortII
 * @Description: 324. 摆动排序 II
 * @Author: zhaooo
 * @Date: 2022/6/28/14:07
 */
public class WiggleSortII {
    /**
     * len为数组长度，则数组中相同元素最多为 ⌊(len+1)/2⌋ 个,令其为x
     * 则排序后的数组中 nums[i] < num[i+x],可得第一组大小关系
     * 再分类讨论：
     *  len 为偶数时:
     *   num[i-1] < num[i+x] > nums[i]
     *   i 从 0 到 len - 1 -x,此时 x = len / 2
     *   因为第一位为num[x],最后一位为num[len - x - 1] = num[x - 1],故插入后再逆序
     *  len 为奇数时:
     *   相同元素最多为x个,x比剩下部分多1
     *   故第一位为num[0],此时逆序与逆序大小关系不变
     */
    public void wiggleSort(int[] nums) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        int n = nums.length;
        int x = (n + 1) / 2;
        // 从arr中取元素逆序插入,j时较小数下标,k为较大数下标
        // i<n以较小数的插入次数为判断条件
        for (int i = 0, j = x - 1, k = n - 1; i < n; i += 2, j--, k--) {
            nums[i] = arr[j];
            // 为奇数时不插入下一个
            if (i + 1 < n) {
                nums[i + 1] = arr[k];
            }
        }
    }

    Random random = new Random();

    /**
     * 三向切分
     * @param nums
     */
    public void wiggleSort2(int[] nums) {
        int n = nums.length;
        int x = (n + 1) / 2;
        int mid = x - 1;
        int target = findKthLargest(nums, n - mid);
        for (int k = 0, i = 0, j = n - 1; k <= j; k++) {
            if (nums[k] > target) {
                while (j > k && nums[j] > target) {
                    j--;
                }
                swap(nums, k, j--);
            }
            if (nums[k] < target) {
                swap(nums, k, i++);
            }
        }
        int[] arr = nums.clone();
        for (int i = 0, j = x - 1, k = n - 1; i < n; i += 2, j--, k--) {
            nums[i] = arr[j];
            if (i + 1 < n) {
                nums[i + 1] = arr[k];
            }
        }
    }

    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quickSelect(int[] a, int l, int r, int index) {
        int q = randomPartition(a, l, r);
        if (q == index) {
            return a[q];
        } else {
            return q < index ? quickSelect(a, q + 1, r, index) : quickSelect(a, l, q - 1, index);
        }
    }

    public int randomPartition(int[] a, int l, int r) {
        int i = random.nextInt(r - l + 1) + l;
        swap(a, i, r);
        return partition(a, l, r);
    }

    public int partition(int[] a, int l, int r) {
        int x = a[r], i = l - 1;
        for (int j = l; j < r; ++j) {
            if (a[j] <= x) {
                swap(a, ++i, j);
            }
        }
        swap(a, i + 1, r);
        return i + 1;
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
