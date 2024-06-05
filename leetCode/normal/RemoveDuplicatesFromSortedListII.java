package normal;

import dependency.ListNode;

/**
 * @ClassName: RemoveDuplicatesFromSortedListII
 * @Description: 82. 删除排序链表中的重复元素 II
 * @Author: zhaooo
 * @Date: 2023/07/19 14:55
 */
public class RemoveDuplicatesFromSortedListII {
    /**
     * 遍历
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-100);
        dummy.next = head;
        // 当前节点，当前节点前一个节点
        ListNode cur = head, pre = dummy;
        while (cur != null) {
            int val = cur.val;
            ListNode tmp = cur;
            while (tmp != null && tmp.val == val) {
                tmp = tmp.next;
            }
            // pre->cur->tmp/null
            if (cur.next == tmp) {
                pre = pre.next;
                cur = cur.next;
            }else {
                // pre->cur->xxx->tmp/null
                pre.next = tmp;
                cur = tmp;
            }
        }
        return dummy.next;
    }


    /**
     * 递归
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates2(ListNode head) {
        // baseCase
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        // 如果是这种情况
        //      1 --> 1 --> 1 --> 2 --> 3
        //     head  next
        // 1.则需要移动next直到出现与当前head.value不相等的情况（含null）
        // 2.并且此时的head已经不能要了，因为已经head是重复的节点
        //--------------else-------------
        //      1 --> 2 --> 3
        //     head  next
        // 3.如果没有出现1的情况，则递归返回的节点就作为head的子节点
        if (head.val == next.val) {
            // 1
            while (next != null && head.val == next.val) {
                next = next.next;
            }
            // 2
            head = deleteDuplicates2(next);
        } else {
            // 3
            head.next = deleteDuplicates2(next);
        }
        return head;
    }
}
