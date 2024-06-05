package normal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: EvaluateReversePolishNotation
 * @Description: 150. 逆波兰表达式求值
 * @Author: zhaooo
 * @Date: 2023/11/09 09:45
 */
public class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        // 操作数栈
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.addLast(stack.removeLast() + stack.removeLast());
                    break;
                case "-":
                    int val2 = stack.removeLast();
                    int val1 = stack.removeLast();
                    stack.addLast(val1 - val2);
                    break;
                case "*":
                    stack.addLast(stack.removeLast() * stack.removeLast());
                    break;
                case "/":
                    int valB = stack.removeLast();
                    int valA = stack.removeLast();
                    stack.addLast(valA / valB);
                    break;
                default:
                    stack.addLast(Integer.parseInt(token));
                    break;
            }
        }
        // switch 表达式
        // for (String token : tokens) {
        //     stack.addLast(switch (token) {
        //         case "+" -> stack.removeLast() + stack.removeLast();
        //         case "-" -> {
        //             int val2 = stack.removeLast();
        //             int val1 = stack.removeLast();
        //             yield val1 - val2;
        //         }
        //         case "*" -> stack.removeLast() * stack.removeLast();
        //         case "/" -> {
        //             int valB = stack.removeLast();
        //             int valA = stack.removeLast();
        //             yield valA / valB;
        //         }
        //         default -> Integer.parseInt(token);
        //     });
        // }
        return stack.removeLast();
    }
}
