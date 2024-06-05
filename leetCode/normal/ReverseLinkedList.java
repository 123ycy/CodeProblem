package normal;

import dependency.ListNode;

/**
 * @ClassName: ReverseLinkedList
 * @Description: 206. 反转链表
 * @Author: zhaooo
 * @Date: 2023/10/22 14:03
 */
public class ReverseLinkedList {
    /**
     * 递归
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);
        // 通过当前节点（head）找到当前节点的前一个节点，并与之相连
        head.next.next = head;
        // 与当前节点的下一个节点相连
        head.next = null;
        return newHead;
    }

    /**
     * 双指针，动态改变链表每个指针的方向
     *
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    /**
     * dummy节点
     * @param head
     * @return
     */
    public ListNode reverseList3(ListNode head) {
        ListNode dummy = new ListNode();
        ListNode tmp = dummy;
        while (head != null) {
            tmp = head.next;
            head.next = dummy.next;
            dummy.next = head;
            head = tmp;
        }
        return dummy.next;
    }
}
