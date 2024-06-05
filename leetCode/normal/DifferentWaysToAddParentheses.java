package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DifferentWaysToAddParentheses
 * @Description: 241. 为运算表达式设计优先级
 * @Author: zhaooo
 * @Date: 2022/7/1/14:46
 */
public class DifferentWaysToAddParentheses {
    public List<Integer> diffWaysToCompute(String expression) {
        int opNum = 0;
        char[] chars = expression.toCharArray();
        for (char c : chars) {
            if (c < '0') {
                opNum++;
            }
        }
        int[] nums = new int[opNum + 1];
        char[] ops = new char[opNum];
        for (int i = 0, ni = 0, oi = 0, tmp = 0; ; i++) {
            if (i == chars.length) {
                nums[ni] = tmp;
                break;
            }
            if (chars[i] < '0') {
                ops[oi++] = chars[i];
                nums[ni++] = tmp;
                tmp = 0;
            } else {
                tmp = tmp * 10 + chars[i] - '0';
            }
        }
        return dfs(0, opNum, nums, ops);
    }

    public List<Integer> dfs(int l, int r, int[] nums, char[] ops) {
        List<Integer> list = new ArrayList<>();
        if (l == r) {
            list.add(nums[l]);
            return list;
        }
        // mid表示将nums分为[l,mid]和[mid+1,r]两部分
        // 两部分之间的操作符为ops[mid],因为ops的下标从0开始
        // switch表达式是从Java 12开始支持
        // for (int mid = l; mid < r; mid++) {
        //     for (int num1 : dfs(l, mid, nums, ops)) {
        //         for (int num2 : dfs(mid + 1, r, nums, ops)) {
        //             list.add(switch (ops[mid]) {
        //                 case '+' -> num1 + num2;
        //                 case '-' -> num1 - num2;
        //                 case '*' -> num1 * num2;
        //                 default -> 0;
        //             });
        //         }
        //     }
        // }
        for (int mid = l; mid < r; mid++) {
            for (int num1 : dfs(l, mid, nums, ops)) {
                for (int num2 : dfs(mid+1, r, nums, ops)) {
                    int tmp = 0;
                    switch (ops[mid]) {
                        case '+': {
                            tmp = num1 + num2;
                            break;
                        }
                        case '-': {
                            tmp = num1 - num2;
                            break;
                        }
                        case '*': {
                            tmp = num1 * num2;
                            break;
                        }
                        default:
                            break;
                    }
                    list.add(tmp);
                }
            }
        }
        return list;
    }
}
