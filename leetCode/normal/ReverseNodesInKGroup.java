package normal;

import dependency.ListNode;

/**
 * @ClassName: ReverseNodesInKGroup
 * @Description: 25. K 个一组翻转链表
 * @Author: zhaooo
 * @Date: 2023/3/14/10:26
 */
public class ReverseNodesInKGroup {
    int counter = 0;

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(), pre = dummy, cur = head;
        dummy.next = head;
        // 是否还有下一个k组的头节点
        while (pre.next != null) {
            counter = 0;
            cur = pre.next;
            // 此时 head->xxx->cur/null->node/null
            head = reverse(cur, k);
            // 不足k个
            if (counter < k) {
                k = counter;
                counter = 0;
                // 反转最后counter个
                head = reverse(head, k);
            }
            pre.next = head;
            pre = cur;
        }
        return dummy.next;
    }

    private ListNode reverse(ListNode head, int k) {
        ListNode pre = null, cur = head;
        while (counter < k && cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            counter++;
        }
        // (pre->xxx->head)->cur/null
        // 将当前k组和下一组连接
        head.next = cur;
        return pre;
    }
}
