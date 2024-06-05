package normal;

import dependency.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: PalindromeLinkedList
 * @Description: 234. 回文链表
 * @Author: zhaooo
 * @Date: 2023/10/26 13:40
 */
public class PalindromeLinkedList {
    /**
     * 快慢指针+栈 保存前一半的元素，后一半遍历
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        ListNode fast = head, slow = head;
        Deque<ListNode> stack = new ArrayDeque<>();
        stack.addLast(head);
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            stack.addLast(slow);
        }
        // slow是中间节点，奇数个节点时，slow指向中间节点，偶数个节点时，slow指向中间偏右节点
        slow = fast.next == null ? slow : slow.next;
        while (slow != null) {
            if (slow.val != stack.removeLast().val) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    /**
     * 递归
     */
    ListNode frontPointer;

    public boolean isPalindrome2(ListNode head) {
        frontPointer = head;
        return recursivelyCheck(head);
    }

    private boolean recursivelyCheck(ListNode currentNode) {
        // 递归到最底层时frontPointer指向head
        if (currentNode != null) {
            if (!recursivelyCheck(currentNode.next)) {
                return false;
            }
            if (currentNode.val != frontPointer.val) {
                return false;
            }
            // currentNode校验完成后frontPointer指向下一个节点
            // 递归返回上一层时currentNode指向上一个节点
            // frontPointer会从head遍历到tail，相当于每个元素都判断了2次回文
            frontPointer = frontPointer.next;
        }
        return true;
    }

    /**
     * 快慢指针+反转链表
     *
     * @param head
     * @return
     */
    public boolean isPalindrome3(ListNode head) {
        // slow指向中点（奇数为中点，偶数为中2点的左元素）pre指向slow的前一个，next指向slow的后一个
        ListNode fast = head, slow = head, next = head.next, pre = null;
        while (fast.next != null && fast.next.next != null) {
            // 先移动fast，防止修改的指针影响fast
            fast = fast.next.next;
            pre = slow;
            slow = next;
            next = next.next;
            slow.next = pre;
        }
        // 当奇数个节点时slow指向前一个，跳过中间节点验证
        slow = fast.next == null ? pre : slow;
        while (next != null) {
            if (next.val != slow.val) {
                return false;
            }
            next = next.next;
            slow = slow.next;
        }
        return true;
    }

    /**
     * 计算hash
     * https://leetcode.cn/problems/palindrome-linked-list/solutions/1393721/xin-si-lu-by-super9du-tnil/
     *
     * @param head
     * @return
     */
    public boolean isPalindrome4(ListNode head) {
        ListNode t = head;
        int base = 11, mod = 1000000007;
        int left = 0, right = 0, mul = 1;
        while (t != null) {
            left = (int) (((long) left * base + t.val) % mod);
            right = (int) ((right + (long) mul * t.val) % mod);
            mul = (int) ((long) mul * base % mod);
            t = t.next;
        }
        return left == right;
    }
}
