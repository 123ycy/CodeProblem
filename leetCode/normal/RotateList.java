package normal;

import dependency.ListNode;

/**
 * @ClassName: RotateList
 * @Description: 61. 旋转链表
 * @Author: zhaooo
 * @Date: 2023/07/20 10:30
 */
public class RotateList {

    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null || head.next == null) {
            return head;
        }
        ListNode tmp = head;
        int len = 1;
        while (tmp.next != null) {
            tmp = tmp.next;
            len++;
        }
        k %= len;
        // 取右侧的k个等于取左侧的len-k个
        k = len - k;
        // 连接成环
        tmp.next = head;
        while (k > 0) {
            tmp = tmp.next;
            k--;
        }
        // 新头节点
        head = tmp.next;
        // 断开连接
        tmp.next = null;
        return head;
    }
}
