package normal;

import dependency.ListNode;

/**
 * @ClassName: MergeTwoSortedLists
 * @Description: 21. 合并两个有序链表
 * @Author: zhaooo
 * @Date: 2024/01/15 14:30
 */
public class MergeTwoSortedLists {
    /**
     * 递归
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }

    /**
     * dummy节点
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        ListNode newHead = new ListNode(-1);
        ListNode cur = newHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                cur.next = list1;
                cur = cur.next;
                list1 = list1.next;
            } else {
                cur.next = list2;
                cur = cur.next;
                list2 = list2.next;
            }
        }
        cur.next = list1 == null ? list2 : list1;
        return newHead.next;
    }

    // 合并链表并去除重复节点
    public ListNode mergeTwoListsWithoutSameValue2(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        while (list1 != null || list2 != null) {
            // 合并后链表与原链表的连接
            cur.next = null;
            while (list1 != null && list1.val == cur.val) {
                list1 = list1.next;
            }
            while (list2 != null && list2.val == cur.val) {
                list2 = list2.next;
            }
            if (list1 == null && list2 == null) {
                return dummy.next;
            }
            // 最多一个null
            // list1为null 或者 2个都不为null
            if (list1 == null || (list2 != null && list1.val >= list2.val)) {
                cur.next = list2;
                cur = cur.next;
                list2 = list2.next;
            } else {
                // list1 != null
                cur.next = list1;
                cur = cur.next;
                list1 = list1.next;
            }
        }
        return dummy.next;
    }
}
