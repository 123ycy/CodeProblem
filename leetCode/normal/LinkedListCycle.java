package normal;

import dependency.ListNode;

/**
 * @ClassName: LinkedListCycle
 * @Description: 141. 环形链表 
 * @Author: zhaooo
 * @Date: 2024/01/29 13:08
 */
public class LinkedListCycle {
    /**
     * 缩小范围 环一直存在
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        while (head != null && head.next != null) {
            // 无论head是环还是 head.next是环
            // 缩小后环依然存在
            head.next = head.next.next;
            if (head == head.next)
                return true;
            else
                head = head.next;
        }
        return false;
    }

    /**
     * 快慢指针
     * @param head
     * @return
     */
    public boolean hasCycle2(ListNode head) {
        ListNode fast = head, slow = head;
        // 找到第一次相等的节点 没相等的节点直接退出
        // fast必slow多走nb即 nb + slow = fast, fast = 2slow
        // 推出 slow = nb
        do {
            if (fast == null || fast.next == null) {
                return false;
            }
            fast = fast.next.next;
            slow = slow.next;
        } while (fast != slow);
        return true;
    }
}
