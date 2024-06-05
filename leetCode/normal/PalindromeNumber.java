package normal;

/**
 * @ClassName: PalindromeNumber
 * @Description: 9. 回文数
 * @Author: zhaooo
 * @Date: 2023/04/15 16:21
 */
public class PalindromeNumber {
    /**
     * 队列
     * 从头到尾构建一个反向的数
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x == 0) {
            return true;
        }
        int tmp = x, top = 0, tail = 0, reverse = 0;
        int[] queue = new int[10];
        // 如果x=0 会无限循环
        while (tmp % 10 == 0) {
            tmp /= 10;
        }
        while (tmp > 0) {
            queue[top++] = tmp % 10;
            tmp /= 10;
        }
        while (tail < top) {
            reverse *= 10;
            reverse += queue[tail++];
        }
        return x == reverse;
    }

    /**
     * string
     *
     * @param x
     * @return
     */
    public boolean isPalindrome2(int x) {
        String reversedStr = (new StringBuilder(String.valueOf(x))).reverse().toString();
        return (String.valueOf(x)).equals(reversedStr);
    }

    /**
     * 数学
     * 两边同时判断
     * https://leetcode.cn/problems/palindrome-number/solutions/6170/dong-hua-hui-wen-shu-de-san-chong-jie-fa-fa-jie-ch/?orderBy=most_votes
     *
     * @param x
     * @return
     */
    public boolean isPalindrome3(int x) {
        // 边界判断
        if (x < 0) return false;
        int div = 1;
        // 找到最大位数
        while (x / div >= 10) div *= 10;
        while (x > 0) {
            int left = x / div;
            int right = x % 10;
            if (left != right) return false;
            // 去掉最大和最小位
            x = (x % div) / 10;
            // div相当于少了2位
            div /= 100;
        }
        return true;
    }

    /**
     * 取出后半段数字进行翻转
     *
     * @param x
     * @return
     */
    public boolean isPalindrome4(int x) {
        // 负数或者 x!=0时末尾为0 直接返回
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int revertedNumber = 0;
        // revertedNumber >= x 表示已经取出一半数字
        while (x > revertedNumber) {
            // 不断讲取出的数字放在revertedNumber末尾
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }
        // x的长度分别为奇偶
        return x == revertedNumber || x == revertedNumber / 10;
    }
}
