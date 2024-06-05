package normal;

import dependency.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: RemoveNthNodeFromEndOfList
 * @Description: 19. 删除链表的倒数第 N 个结点
 * @Author: zhaooo
 * @Date: 2023/10/20 19:33
 */
public class RemoveNthNodeFromEndOfList {
    /**
     * 遍历 记录长度
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        int length = 0;
        while (head != null) {
            ++length;
            head = head.next;
        }
        ListNode cur = dummy;
        for (int i = 1; i < length - n + 1; ++i) {
            cur = cur.next;
        }
        cur.next = cur.next.next;
        return dummy.next;
    }

    /**
     * 栈
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        Deque<ListNode> stack = new ArrayDeque<>();
        ListNode cur = head;
        while (cur != null) {
            stack.addLast(cur);
            cur = cur.next;
        }
        while (n > 0) {
            cur = stack.removeLast();
            n--;
        }
        if (stack.isEmpty()) {
            return head.next;
        }
        stack.removeLast().next = cur.next;
        return head;
    }

    /**
     * 快慢指针
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd3(ListNode head, int n) {
        ListNode fast = head, slow = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        ListNode pre = null;
        while (fast != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next;
        }
        if (pre == null) {
            return head.next;
        }
        pre.next = slow.next;
        return head;
    }

    /**
     * 递归
     */
    int n, m;

    public ListNode removeNthFromEnd4(ListNode head, int n) {
        this.n = n;
        m = 0;
        ListNode dummy = new ListNode(0, head);
        dfs(head, dummy);
        return dummy.next;
    }

    void dfs(ListNode cur, ListNode pre) {
        if (cur == null) {
            return;
        }
        dfs(cur.next, cur);
        if (++m == n) {
            pre.next = cur.next;
        }
    }
}
