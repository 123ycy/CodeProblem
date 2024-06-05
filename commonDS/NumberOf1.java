/**
 * @ClassName: NumberOf1
 * @Description: 1的数量
 * @Author: zhaooo
 * @Date: 2022/6/30/22:17
 */
public class NumberOf1 {
    public int numberOf1(int n) {
        int count = 0;
        while (n != 0) {
            // n-1表示把第一个1之后的所有0变为1,当前1变为0
            n = n & (n - 1);
            count++;
        }
        return count;
    }
}
