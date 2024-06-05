package normal;

import dependency.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: AddTwoNumbersII
 * @Description: 445. 两数相加 II
 * @Author: zhaooo
 * @Date: 2023/10/23 14:52
 */
public class AddTwoNumbersII {
    /**
     * 栈 复用原始节点
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Deque<ListNode> stack1 = new ArrayDeque<>(), stack2 = new ArrayDeque<>();
        ListNode tmp1 = l1, tmp2 = l2;
        // 入栈
        while (tmp1 != null) {
            stack1.addLast(tmp1);
            tmp1 = tmp1.next;
        }
        while (tmp2 != null) {
            stack2.addLast(tmp2);
            tmp2 = tmp2.next;
        }
        // 修改stack1 和l1 指向长的listNode
        if (stack2.size() > stack1.size()) {
            Deque<ListNode> tmp = stack1;
            stack1 = stack2;
            stack2 = tmp;
            l1 = l2;
        }
        int carry = 0;
        // 栈底元素相加，stack2为空carry=0时提前推出
        while (!stack1.isEmpty() && carry != 0 || !stack2.isEmpty()) {
            carry += stack2.isEmpty() ? 0 : stack2.removeLast().val;
            tmp1 = stack1.removeLast();
            tmp1.val += carry;
            carry = tmp1.val / 10;
            tmp1.val %= 10;
        }
        // 有加数
        if (carry > 0) {
            return new ListNode(carry, l1);
        }
        return l1;
    }
}
