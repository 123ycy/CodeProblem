import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: Sort
 * @Description: 排序算法
 * @Author: zhaooo
 * @Date: 2022/7/11/11:05
 */
public class Sort {
    public class QuickSort {
        public int[] sort(int[] arr) {
            quickSort(arr, 0, arr.length - 1);
            return arr;
        }

        private void quickSort(int[] arr, int left, int right) {
            // 每轮确定一个元素的位置
            if (left < right) {
                int pivotPos = partition(arr, left, right);
                quickSort(arr, left, pivotPos - 1);
                quickSort(arr, pivotPos + 1, right);
            }
        }

        private void quickSort2(int[] arr, int left, int right) {
            // 非递归实现quickSort
            Deque<Integer> deque = new ArrayDeque<>();
            deque.addLast(left);
            deque.addLast(right);
            while (!deque.isEmpty()) {
                int r = deque.removeLast();
                int l = deque.removeLast();
                if (l < r) {
                    int pivotPos = partition(arr, l, r);
                    // 可以看作左子树
                    deque.addLast(l);
                    deque.addLast(pivotPos - 1);
                    // 可以看作右子树
                    deque.addLast(pivotPos + 1);
                    deque.addLast(r);
                }
            }
        }

        // 填充
        private int partition(int[] arr, int left, int right) {
            // 取左边界为枢轴，第一个空位为左侧
            // 也可以随机取一点为枢轴
            // 也可以取最左，中间，最右3点的中位数为枢轴
            int pivot = arr[left];
            while (left < right) {
                // 从右至左找到一个小于pivot的数放入左侧空位
                while (left < right && arr[right] >= pivot) {
                    right--;
                }
                arr[left] = arr[right];
                // 从左至右找到大于小于pivot的数放入右侧空位
                while (left < right && arr[left] <= pivot) {
                    left++;
                }
                arr[right] = arr[left];
            }
            // 退出时left==right，pivot放入
            arr[left] = pivot;
            return left;
        }

        // 交换
        private int partition2(int[] arr, int left, int right) {
            // 取左边界为枢轴
            int pivot = arr[left];
            int idx = left;
            // 将大于pivot的数放入右侧
            for (int i = left + 1; i <= right; i++) {
                if (arr[i] < pivot) {
                    // i >= idx+1
                    swap(arr, i, idx + 1);
                    idx++;
                }
            }
            // 排序后[pivot, 大于pivot, 小于pivot] [left ,[left+1, idx], [idx+1, right]]
            // 将pivot放入中间
            swap(arr, left, idx);
            return idx;
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public class HeapSort {
        public int[] heapSort(int[] arr) {
            int len = arr.length;
            // 构建大顶堆，这里其实就是把待排序序列，变成一个大顶堆结构的数组
            buildMaxHeap(arr, len);
            // 交换堆顶和当前末尾的节点，重置大顶堆
            for (int i = len - 1; i > 0; i--) {
                swap(arr, 0, i);
                len--;
                heapify(arr, 0, len);
            }
            return arr;
        }

        private void buildMaxHeap(int[] arr, int len) {
            // 从最后一个非叶节点开始向前遍历，调整节点性质，使之成为大顶堆
            // 下标从0开始的二叉树最后一个非叶节点为 len/2-1
            // 下标从1开始的二叉树最后一个非叶节点为 len/2
            for (int i = (len >> 1) - 1; i >= 0; i--) {
                heapify(arr, i, len);
            }
        }

        private void heapify(int[] arr, int i, int len) {
            // 先根据堆性质，找出它左右节点的索引
            // 下标从0开始的二叉树节点的左右孩子分别为 2*i+1 和 2*i+2
            // 下标从1开始的二叉树节点的左右孩子分别为 2*i 和 2*i+1
            int left = (i << 1) + 1;
            int right = (i << 1) + 2;
            // 默认当前节点（父节点）是最大值。
            int max = i;
            // 找到比当前根更大的子节点
            if (left < len && arr[left] > arr[max]) {
                max = left;
            }
            if (right < len && arr[right] > arr[max]) {
                max = right;
            }
            if (max != i) {
                // 即较小元素下坠（较大元素上升）
                swap(arr, i, max);
                // 因为互换之后，子节点的值变了，如果该子节点也有自己的子节点，仍需要再次调整。
                heapify(arr, max, len);
            }
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public class MergeSort {

        public int[] sort(int[] arr) {
            int len = arr.length;
            mergeSort(arr, 0, len - 1, new int[len]);
            return arr;
        }

        private void mergeSort(int[] nums, int left, int right, int[] temp) {
            // 小区间使用其他排序例如插入排序
            if (left == right) {
                return;
            }
            int mid = (left + right) >> 1;
            mergeSort(nums, left, mid, temp);
            mergeSort(nums, mid + 1, right, temp);
            // 如果数组的这个子区间本身有序（升序），无需合并
            if (nums[mid] <= nums[mid + 1]) {
                return;
            }
            mergeOfTwoSortedArray(nums, left, mid, right, temp);
        }

        private void mergeOfTwoSortedArray(int[] nums, int left, int mid, int right, int[] temp) {
            //[left, mid] 有序，[mid + 1, right] 有序
            // public static native void arraycopy(Object src, int  srcPos, Object dest, int destPos, int length);
            System.arraycopy(nums, left, temp, left, right + 1 - left);
            int i = left;
            int j = mid + 1;
            // 将tmp数组的那个有序部分，双指针有序插入原数组
            for (int k = left; k <= right; k++) {
                // i到底
                if (i == mid + 1) {
                    nums[k] = temp[j++];
                    // j到底
                } else if (j == right + 1) {
                    nums[k] = temp[i++];
                } else if (temp[i] <= temp[j]) {
                    // 注意写成 < 就丢失了稳定性（相同元素原来靠前的排序以后依然靠前）
                    nums[k] = temp[i++];
                } else {
                    // temp[i] > temp[j]
                    nums[k] = temp[j++];
                }
            }
        }
    }

    public class SortList {
        // 148. 排序链表
        // SortList
    }

    public class BubbleSort {
        public int[] bubbleSort(int[] arr) {
            boolean flag = true;
            int len = arr.length;
            // 每次选一个最大的放在后面
            for (int i = len; i > 0; i--) {
                for (int j = 1; j < i; j++) {
                    if (arr[j - 1] > arr[j]) {
                        swap(arr, j - 1, j);
                        flag = false;
                    }
                }
                // 如果没发生交换则有序
                if (flag) {
                    return arr;
                }
            }
            return arr;
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public class BucketSor {
        // 将数组分到有限数量的桶子里。每个桶子再个别排序（有可能再使用别的排序算法或是以递归方式继续使用桶排序进行排序）
        public int[] bucketSort(int[] arr, int max) {
            int[] buckets;
            buckets = new int[max];
            // 记录对应桶元素的出现次数
            for (int k : arr) {
                buckets[k]++;
            }
            // 排序
            for (int i = 0, j = 0; i < max; i++) {
                while ((buckets[i]--) > 0) {
                    arr[j++] = i;
                }
            }
            return arr;
        }
    }

    public class InsertSort {
        public int[] insertSort(int[] arr) {
            int len = arr.length;
            int i, j, k;
            for (i = 1; i < len; i++) {
                // 为a[i]在前面的a[0...i-1]有序区间中找一个合适的位置，然后a[i]插入a[j+1]
                for (j = i - 1; j >= 0; j--) {
                    if (arr[j] < arr[i]) {
                        break;
                    }
                }
                // 如找到了一个合适的位置
                if (j != i - 1) {
                    // 将比a[i]大的数据向后移，将[j+1,i-1]移动到[j+2,i]，然后将a[i]放入[j+1]
                    int temp = arr[i];
                    for (k = i - 1; k > j; k--) {
                        arr[k + 1] = arr[k];
                    }
                    // 将a[i]放到正确位置上
                    arr[k + 1] = temp;
                }
            }
            return arr;
        }
    }

    public class SelectionSort {
        public int[] selectionSort(int[] arr) {
            int len = arr.length;
            // 每轮选择一个最小值与第一个未排序位置交换
            for (int i = 0; i < len; i++) {
                int minIndex = i;
                for (int j = i + 1; j < len; j++) {
                    if (arr[j] < arr[minIndex]) {
                        minIndex = j;
                    }
                }
                swap(arr, i, minIndex);
            }
            return arr;
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public class ShellSort {
        public int[] shellSort(int[] arr) {
            int len = arr.length;
            int h = len >> 1;
            while (h >= 1) {
                // 插入排序
                for (int i = h; i < len; i++) {
                    insertionForDelta(arr, h, i);
                }
                // 步幅每轮除2
                h = h / 2;
            }
            return arr;
        }

        // 内部插入排序
        private void insertionForDelta(int[] arr, int gap, int i) {
            int temp = arr[i];
            int j = i;
            // 从i开始向后移，步幅为gap，i之前的已经有序插入，gap为1即为普通插入排序
            // 找到在该分组中第一个nums[x]<tmp的下一个位置（j=x+gap），即为插入位置
            while (j >= gap && arr[j - gap] > temp) {
                arr[j] = arr[j - gap];
                j -= gap;
            }
            arr[j] = temp;
        }
    }
}
