package normal;

/**
 * @ClassName: SwapAdjacentInLrString
 * @Description: 777. 在LR字符串中交换相邻字符 
 * @Author: zhaooo
 * @Date: 2022/10/2/11:57
 */
public class SwapAdjacentInLrString {

    /**
     * 双指针
     * https://leetcode.cn/problems/swap-adjacent-in-lr-string/solution/by-ac_oier-ye71/
     * @param start
     * @param end
     * @return
     */
    public boolean canTransform(String start, String end) {
        int n = start.length(), i = 0, j = 0;
        while (i < n || j < n) {
            while (i < n && start.charAt(i) == 'X') {
                i++;
            }
            while (j < n && end.charAt(j) == 'X') {
                j++;
            }
            if (i == n || j == n) {
                return i == j;
            }
            if (start.charAt(i) != end.charAt(j)) {
                return false;
            }
            if (start.charAt(i) == 'L' && i < j) {
                return false;
            }
            if (start.charAt(i) == 'R' && i > j) {
                return false;
            }
            i++;
            j++;
        }
        return i == j;
    }
}
