package normal;

import dependency.ListNode;

/**
 * @ClassName: IntersectionOfTwoLinkedLists
 * @Description: 160. 相交链表
 * @Author: zhaooo
 * @Date: 2023/10/22 11:37
 */
public class IntersectionOfTwoLinkedLists {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        // 不相交第二遍遍历 pA = pB = null，返回 null
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}
