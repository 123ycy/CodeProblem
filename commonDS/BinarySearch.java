/**
 * @ClassName: BinarySearch
 * @Description: 二分查找
 * @Author: zhaooo
 * @Date: 2022/8/28/20:28
 */
public class BinarySearch {

    /**
     * lowerBound 返回第一个 >= target的数，即元素下标或者要插入的位置，其他情况可以修改条件得出
     * > target 转化为 >= (target + 1)
     * < target 转化为 (>= target) - 1
     * <= target 转化为  (> target) - 1 转化为 (>= (target + 1)) - 1 即最后一个 <= target的数即元素下标或者要插入的前一个
     * @param nums
     * @param target
     * @return
     */
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
        // < target | >= target
        //        r | l
        return l;
    }

    /**
     * 找到target的下标，没找到返回插入位置
     * 闭区间 [l,r]
     * 1. 确定循环不变量：
     *      [ < target] [l, r] [ >= target]
     *      l的左侧全是小于target的数
     *      r的右侧全是大于等于target的数
     * 2. 由循环不变量 -> 循环条件/退出条件：
     *       < target  |  >= target
     *               r | l
     *      退出条件是没有不确定和target大小相比的数，即[l, r]的大小为0，此时l = r + 1
     *      即 l > r 时退出，注意此时依然满足循环不变量
     * 3. 由循环不变量 -> 循环体：
     *      当nums[mid] == target时，返回答案
     *      当nums[mid] < target时，由于l的左侧全是小于target的数，且mid也小于target，故mid在l左侧，即l = mid + 1
     *      当nums[mid] > target时，由于r的右侧全是大于等于target的数，且mid也大于等于target，故mid在r右侧，即r = mid - 1
     * 4. 由循环不变量 -> 返回值：
     *      由于退出条件是[l, r]的大小为0，此时l = r + 1，且l的左侧全是小于target的数，r的右侧全是大于等于target的数
     *      故[( r < target)r, l (l >= target)]，即返回l或者r + 1
     * 5. 当target在nums范围之外时，返回结果越界：
     *      若target < nums[0]，则l = 0，r = -1，返回l = 0
     *      若target > nums[len - 1]，则l = len，r = len - 1，返回l = len
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        // 可能要求没找到时return -1
        // return l和return r+1都可以
        return l;
    }

    /**
     * 找到target的下标，没找到返回插入位置
     * 左闭右开 [l, r)
     *
     * 寻找方式同闭区间 [l,r]的确定循环不变量
     *
     * 编程中的左闭右开思想：
     *  什么是左闭右开：左闭右开是一种区间表示方式，例如在整数上[3,6)表示3，4，5三个数，闭代表取值取到那个数，开代表取值取不到那个数。
     *  左闭右开的好处：对于一个左闭右开区间[l，r)来说：
     *  能表示单独一个数：若区间内只有一个数我们可以用像[1，2)表示1，注意[x,x]不符合数学上区间的定义(左区间比右区间大)
     *  便于统计区间内个数：r减l正好是区间内元素的个数，对于左闭右闭区间来说r-l+1才是区间内元素　　
     *  便于表示空集：空集可以用[x，x)表示
     *  便于切割区间：例如我们要在区间内找到一个切割点x，并把x左边归为一个区间，x和x右边归为一个区间则切割后的区间就可以用[l，x)和[x，r)表示
     *  和数组下标相匹配：对于一个从0开始的长度为n的数组来说，[0，n）正好表示这个数组的所有下标，如果用闭区间则要用[0，n-1]来表示
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        // return r和return l都可以
        return l;
    }

    /**
     * 闭区间递归
     * 寻找方式同闭区间 [l,r]的确定循环不变量
     * @param nums
     * @param target
     * @return
     */
    public int search3(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        return dfs(nums, l, r, target);
    }

    public int dfs(int[] nums, int l, int r, int target) {
        if (l > r) {
            return l;
        }
        int mid = (l + r) >> 1;
        if (nums[mid] == target) {
            return mid;
        }
        if (nums[mid] < target) {
            return dfs(nums, mid + 1, r, target);
        }
        return dfs(nums, l, mid - 1, target);
    }

    /**
     * 开区间递归
     * 寻找方式同闭区间 [l,r]的确定循环不变量
     * @param nums
     * @param target
     * @return
     */
    public int search4(int[] nums, int target) {
        int l = 0, r = nums.length;
        return dfs2(nums, l, r, target);
    }

    public int dfs2(int[] nums, int l, int r, int target) {
        if (l == r) {
            return l;
        }
        int mid = (l + r) >> 1;
        if (nums[mid] == target) {
            return mid;
        }
        if (nums[mid] < target) {
            return dfs2(nums, mid + 1, r, target);
        }
        return dfs2(nums, l, mid, target);
    }
}
