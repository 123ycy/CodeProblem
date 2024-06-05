package normal;

import dependency.ListNode;

/**
 * @ClassName: ReverseLinkedListII
 * @Description: 92. 反转链表 II
 * @Author: zhaooo
 * @Date: 2024/04/07 22:16
 */
public class ReverseLinkedListII {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == 1) {
            return reverse(head, right);
        }
        ListNode preNode = head;
        for (int i = 1; i < left - 1; i++) {
            preNode = preNode.next;
        }
        preNode.next = reverse(preNode.next, right - left + 1);
        return head;
    }

    ListNode reverse(ListNode head, int len) {
        ListNode pre = null, cur = head;
        while (len > 0) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            len--;
        }
        head.next = cur;
        return pre;
    }
}
