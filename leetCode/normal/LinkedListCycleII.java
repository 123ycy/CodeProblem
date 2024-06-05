package normal;

import dependency.ListNode;

/**
 * @ClassName: LinkedListCycleII
 * @Description: 142. 环形链表 II
 * @Author: zhaooo
 * @Date: 2022/9/1/16:58
 */
public class LinkedListCycleII {
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        // 找到第一次相等的节点 没相等的节点直接退出
        // fast必slow多走nb即 nb + slow = fast, fast = 2slow
        // 推出 slow = nb
        do {
            if (fast == null || fast.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        } while (fast != slow);
        // slow再走a步 总共a+nb
        // fast走a步 总共a
        // b为环的路径 b步会回到环的起点 两者相遇
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }
}
