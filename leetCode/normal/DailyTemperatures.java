package normal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @ClassName: DailyTemperatures
 * @Description: 739. 每日温度
 * @Author: zhaooo
 * @Date: 2023/11/11 13:14
 */
public class DailyTemperatures {
    /**
     * 单调栈 从右到左（先访问尾元素）
     * 维护一个存储下标的单调栈，从栈底到栈顶的下标对应的温度列表中的温度依次递减。如果一个下标在单调栈里，则表示该温度不是某一温度的更高温度
     * 即每次访问一个温度找到该温度之后的下一个更高温度
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] ans = new int[temperatures.length];
        for (int i = temperatures.length - 1; i >= 0; i--) {
            while (!stack.isEmpty()) {
                if (temperatures[i] < temperatures[stack.getLast()]) {
                    ans[i] = stack.getLast() - i;
                    stack.addLast(i);
                    break;
                } else {
                    stack.removeLast();
                }
            }
            if (stack.isEmpty()) {
                stack.addLast(i);
                ans[i] = 0;
            }
            // 简洁写法
            // while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.getLast()]) {
            //     stack.removeLast();
            // }
            // ans[i] = stack.isEmpty() ? 0 : stack.getLast() - i;
            // stack.addLast(i);
        }
        return ans;
    }

    /**
     * 单调栈 从左到右
     * 维护一个存储下标的单调栈，从栈底到栈顶的下标对应的温度列表中的温度依次递减。如果一个下标在单调栈里，则表示尚未找到下一次温度更高的下标。
     * 即每次访问一个温度时判断该温度是之前哪些温度的下一个更高温度
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures2(int[] temperatures) {
        int length = temperatures.length;
        int[] ans = new int[length];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            int temperature = temperatures[i];
            while (!stack.isEmpty() && temperature > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                ans[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return ans;
    }
}
