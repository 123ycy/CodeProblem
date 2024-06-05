package normal;

/**
 * @ClassName: ValidParentheses
 * @Description: 20. 有效的括号
 * @Author: zhaooo
 * @Date: 2024/01/28 12:18
 */
public class ValidParentheses {
    public boolean isValid(String s) {
        char[] chars = s.toCharArray();
        // top表示已经插入的位置
        int top = -1, len = chars.length;
        // 只需要保存一半的括号，栈溢出则错误
        char[] stack = new char[len / 2];
        for (char c : chars) {
            switch (c) {
                case ']': {
                    if (top < 0 || stack[top] != '[') return false;
                    top--;
                    break;
                }
                case '}': {
                    if (top < 0 || stack[top] != '{') return false;
                    top--;
                    break;
                }
                case ')': {
                    if (top < 0 || stack[top] != '(') return false;
                    top--;
                    break;
                }
                default:
                    if (++top == len / 2) return false;
                    stack[top] = c;
            }
        }
        // 判断栈中是否还有元素
        return top == -1;
    }
}
