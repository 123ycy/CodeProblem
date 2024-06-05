package normal;

import dependency.ListNode;

/**
 * @ClassName: SortList
 * @Description: 148. 排序链表
 * @Author: zhaooo
 * @Date: 2023/12/28 13:34
 */
public class SortList {
    /**
     * 归并排序
     * 时间复杂度 nlogn
     * 空间复杂度 logn（因为递归调用）
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        return sortList(head, null);
    }

    public ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return null;
        }
        // 保证分治时链表是断开的
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        ListNode mid = slow;
        ListNode list1 = sortList(head, mid);
        ListNode list2 = sortList(mid, tail);
        return merge(list1, list2);
    }

    /**
     * 归并排序 自底向上调用
     * 时间复杂度 nlogn
     * 空间复杂度 1
     * @param head
     * @return
     */
    public ListNode sortList2(ListNode head) {
        if (head == null) {
            return head;
        }

        // 1. 首先从头向后遍历,统计链表长度
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }

        // 2. 初始化 引入dummyHead
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;

        // 3. 每次将链表拆分成若干个长度为subLen的子链表 , 并按照每两个子链表一组进行合并
        for (int subLen = 1; subLen < length; subLen <<= 1) {
            ListNode prev = dummyHead;
            // 从curr开始拆分
            ListNode curr = dummyHead.next;
            // 链表按照当前subLen拆分和合并完毕
            while (curr != null) {
                // 3.1 拆分subLen长度的链表1
                ListNode head_1 = curr;
                // 拆分出长度为subLen的链表1，i = 1表明遍历结束时状态为[head->xxx->curr]，head和curr指向闭区间
                // curr等于null时是拆分链表到题目所给链表末尾，即拆分链表长度可能不足subLen
                for (int i = 1; i < subLen && curr.next != null; i++) {
                    curr = curr.next;
                }

                // 3.2 拆分subLen长度的链表2
                ListNode head_2 = curr.next;
                // 断开连接
                curr.next = null;
                curr = head_2;
                if (curr != null) {
                    for (int i = 1; i < subLen && curr.next != null; i++) {
                        curr = curr.next;
                    }
                }

                // nextHead记录下两条链表头部  此时curr指向当前两条链表尾部
                ListNode nextHead = null;
                // curr为null时无下两条链表
                if (curr != null) {
                    nextHead = curr.next;
                    // 断开连接
                    curr.next = null;
                }

                // 3.4 合并两个subLen长度的有序链表
                prev.next = merge(head_1, head_2);
                // 每条子链在拆分后尾部都是null，只有在merge后两个链表合并，合并后的链表尾部还是null
                while (prev.next != null) {
                    prev = prev.next;
                }
                // 链接本轮已排序两条链表与此轮未排序其他链表
                curr = nextHead;
            }
        }
        // 返回新排好序的链表
        return dummyHead.next;
    }

    /**
     * 快速排序
     * @param head
     * @return
     */
    public ListNode sortList3(ListNode head) {
        return quickSortList(head)[0];
    }

    // 返回head排序后的头尾节点
    public ListNode[] quickSortList(ListNode head) {
        if (head == null || head.next == null) {
            return new ListNode[]{head, head};
        }
        ListNode cur = head.next, lHead = null, rHead = null;
        // 取head为pivot 逆序会退化为O(n)
        // 断开head和后面的链接
        head.next = null;
        while (cur != null) {
            ListNode tmp = cur.next;
            // 头插
            if (cur.val < head.val) {
                // lHead(cur)->x->x->null
                // cur.next = null;
                cur.next = lHead;
                lHead = cur;
            } else {
                // rHead(cur)->x->x->null
                cur.next = rHead;
                rHead = cur;
            }
            cur = tmp;
        }
        // 分治
        ListNode[] lSorted = quickSortList(lHead);
        ListNode[] rSorted = quickSortList(rHead);
        // 连接pivot和左右链表
        if (lSorted[1] != null) {
            lSorted[1].next = head;
        }
        if (rSorted[0] != null) {
            head.next = rSorted[0];
        }
        ListNode l = lSorted[0] == null ? head : lSorted[0];
        ListNode r = rSorted[1] == null ? head : rSorted[1];
        return new ListNode[]{l, r};
    }

    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0), temp = dummyHead;
        // 头插法
        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                temp.next = head1;
                head1 = head1.next;
            } else {
                temp.next = head2;
                head2 = head2.next;
            }
            temp = temp.next;
        }
        if (head1 == null) {
            temp.next = head2;
        } else {
            temp.next = head1;
        }
        return dummyHead.next;
    }
}
