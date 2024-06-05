package normal;

import java.util.Arrays;

/**
 * @ClassName: normal.MaximumSwap
 * @Description: 670. 最大交换
 * @Author: zhaooo
 * @Date: 2022/9/13/10:18
 */
public class MaximumSwap {
    /**
     * 单调栈
     * @param num
     * @return
     */
    public int maximumSwap(int num) {
        int[] nums = new int[9];
        int[] stack = new int[9];
        int i = 0, top = -1, res = num;
        while (num > 0) {
            nums[i++] = num % 10;
            num /= 10;
        }
        for (int j = 0; j < i; j++) {
            // 小根堆
            if (top == -1 || nums[j] < nums[stack[top]]) {
                stack[++top] = j;
            } else {
                // 每次将要出栈时改变堆顶和堆底元素在nums中的位置 并比较大小
                res = Math.max(res, numsToNum(nums, i, stack[top], stack[0]));
                // 是>而不是>= 到第一个比他小的位置截至 即最靠经末尾的最小保留
                while (top > -1 && nums[j] > nums[stack[top]]) {
                    top--;
                }
                stack[++top] = j;
            }
        }
        res = Math.max(res, numsToNum(nums, i, stack[top], stack[0]));
        return res;
    }

    void swap(int i, int j, int[] nums) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    int numsToNum(int[] nums, int length, int i, int j) {
        int num = 0;
        // 修改位置
        swap(i, j, nums);
        // 数组转为数
        for (int k = length - 1; k >= 0; k--) {
            num *= 10;
            num += nums[k];
        }
        // 改回位置
        swap(i, j, nums);
        return num;
    }

    /**
     * 贪心
     * 将数字从大到小排列，与原数字比较，找出第一位置不一样的数。
     * 如8217排序后变为8721，两两对比，第二个数不同，表示7和2交换，得到结果8712
     * @param num
     * @return
     */
    public int maximumSwap2(int num) {
        // 排序数组元素
        char[] orderNum = Integer.toString(num).toCharArray();
        char[] oldNum = Integer.toString(num).toCharArray();
        //这里是从小到大排列
        Arrays.sort(orderNum);
        int diff = -1;
        // 比较第一个不同的元素
        for (int i = 0; i < orderNum.length; i++) {
            if (oldNum[i] != orderNum[orderNum.length - 1 - i]) {
                diff = i;
                break;
            }
        }
        // 两数相同，不需要交换
        if (diff == -1) {
            return num;
        }
        // 两两交换
        for (int i = oldNum.length - 1; i >= diff; i--) {
            if (oldNum[i] == orderNum[orderNum.length - 1 - diff]) {
                //交换后直接跳出
                swap(oldNum, diff, i);
                break;
            }
        }
        return Integer.parseInt(new String(oldNum));
    }
    void swap(char[] chars, int lo, int hi) {
        char tmp = chars[lo];
        chars[lo] = chars[hi];
        chars[hi] = tmp;
    }
}
