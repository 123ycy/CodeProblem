package normal;

import dependency.ListNode;

/**
 * @ClassName: PartitionList
 * @Description: 86. 分隔链表
 * @Author: zhaooo
 * @Date: 2023/07/21 11:41
 */
public class PartitionList {
    public ListNode partition(ListNode head, int x) {
        ListNode l1 = new ListNode(), l2 = new ListNode(), l1Tmp = l1, l2Tmp = l2;
        while (head != null) {
            if (head.val < x) {
                l1Tmp.next = head;
                l1Tmp = l1Tmp.next;
            } else {
                l2Tmp.next = head;
                l2Tmp = l2Tmp.next;
            }
            head = head.next;
        }
        l1Tmp.next = l2.next;
        l2Tmp.next = null;
        return l1.next;
    }
}
