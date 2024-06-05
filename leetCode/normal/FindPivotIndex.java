package normal;

/**
 * @ClassName: FindPivotIndex
 * @Description: 724. 寻找数组的中心下标
 * @Author: zhaooo
 * @Date: 2022/8/29/15:56
 */
public class FindPivotIndex {
    /**
     * 动态规划 左右前缀和
     * @param nums
     * @return
     */
    public int pivotIndex(int[] nums) {
        int len = nums.length;
        // rdp[i]表示(i,len-1]之间的元素和，不包含i
        int[] rdp = new int[len];
        // l表示当前下标左侧元素和，不包含当前下标
        int l = 0;
        for (int i = len - 2; i >= 0; i--) {
            rdp[i] = nums[i + 1];
            rdp[i] += rdp[i + 1];
        }
        for (int i = 0; i < len; i++) {
            // 如果当前下标左侧和等于右侧和返回
            if (l == rdp[i]) {
                return i;
            }
            l += nums[i];
        }
        return -1;
    }

    /**
     * 左右前缀和
     * @param nums
     * @return
     */
    public int pivotIndex2(int[] nums) {
        int sumLeft = 0, sumRight = 0;
        for (int i : nums) {
            sumRight += i;
        }
        for (int i = 0; i < nums.length; i++) {
            sumRight -= nums[i];
            // 若左侧元素和等于右侧元素和，返回中心下标 i
            if (sumLeft == sumRight) {
                return i;
            }
            sumLeft += nums[i];
        }
        return -1;
    }

    /**
     * 一个前缀和
     * @param nums
     * @return
     */
    public int pivotIndex3(int[] nums) {
        int sumL = 0, sum = 0;
        for (int i : nums) {
            sum += i;
        }
        for (int i = 0; i < nums.length; i++) {
            // 若当前元素值+左侧元素*2=所有元素和 返回
            if ((sumL << 1) + nums[i] == sum) {
                return i;
            }
            sumL += nums[i];
        }
        return -1;
    }
}
