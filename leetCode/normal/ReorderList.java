package normal;

import dependency.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: ReorderList
 * @Description: 143. 重排链表
 * @Author: zhaooo
 * @Date: 2023/3/18/15:36
 */
public class ReorderList {
    /**
     * 栈+双指针
     * @param head
     */
    public void reorderList(ListNode head) {
        Deque<ListNode> stack = new ArrayDeque<>();
        ListNode tmp = head, cur = head, pre = head;
        int len = 0;
        while (tmp != null) {
            len++;
            stack.addLast(tmp);
            tmp = tmp.next;
        }
        for (int i = 0; i < (len + 1) / 2; i++) {
            pre = cur;
            tmp = cur.next;
            cur.next = stack.removeLast();
            cur.next.next = tmp;
            cur = tmp;
        }
        if ((len & 1) == 1) {
            pre.next = null;
        } else {
            cur.next = null;
        }
    }

    /**
     * 递归
     * https://leetcode.cn/problems/reorder-list/solutions/32910/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-34/
     * @param head
     */
    public void reorderList2(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        int len = 0;
        ListNode tmp = head;
        // 求出节点数
        while (tmp != null) {
            len++;
            tmp = tmp.next;
        }
        dfs(head, len);
    }

    ListNode dfs(ListNode head, int len) {
        if (len == 1) {
            ListNode outTail = head.next;
            head.next = null;
            return outTail;
        }
        if (len == 2) {
            ListNode outTail = head.next.next;
            head.next.next = null;
            return outTail;
        }
        // 得到本层对应的尾节点，并且将头结点和尾节点之间的链表通过递归处理
        ListNode tail = dfs(head.next, len - 2);
        // 外层尾节点
        ListNode outTail = tail.next;
        // 中间链表的头结点
        ListNode subHead = head.next;
        // 连接本层和内层
        head.next = tail;
        tail.next = subHead;
        return outTail;
    }
}
