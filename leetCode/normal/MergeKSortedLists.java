package normal;

import dependency.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @ClassName: MergeKSortedLists
 * @Description: 23. 合并 K 个升序链表
 * @Author: zhaooo
 * @Date: 2023/12/31 15:19
 */
public class MergeKSortedLists {
    /**
     * 堆
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        Queue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.val));
        for (ListNode node : lists) {
            if (node != null) {
                pq.add(node);
            }
        }
        ListNode dummy = new ListNode(), tmp = dummy;
        while (pq.size() > 1) {
            ListNode node = pq.poll();
            if (node.next != null) {
                pq.add(node.next);
            }
            tmp.next = node;
            tmp = tmp.next;
            tmp.next = null;
        }
        // 此时pq只有剩一个链表 或者 一开始就一个没添加
        tmp.next = pq.poll();
        return dummy.next;
    }

    /**
     * 合并 类似归并排序
     * @param lists
     * @return
     */
    public ListNode mergeKLists2(ListNode[] lists) {
        return merge(lists, 0, lists.length - 1);
    }

    // 返回合并后的链表
    public ListNode merge(ListNode[] lists, int l, int r) {
        if (l == r) {
            return lists[l];
        }
        if (l > r) {
            return null;
        }
        int mid = (l + r) >> 1;
        return mergeTwoLists(merge(lists, l, mid), merge(lists, mid + 1, r));
    }

    public ListNode mergeTwoLists(ListNode a, ListNode b) {
        if (a == null || b == null) {
            return a != null ? a : b;
        }
        ListNode head = new ListNode(0);
        ListNode tail = head, aPtr = a, bPtr = b;
        while (aPtr != null && bPtr != null) {
            if (aPtr.val < bPtr.val) {
                tail.next = aPtr;
                aPtr = aPtr.next;
            } else {
                tail.next = bPtr;
                bPtr = bPtr.next;
            }
            tail = tail.next;
        }
        tail.next = (aPtr != null ? aPtr : bPtr);
        return head.next;
    }

}
