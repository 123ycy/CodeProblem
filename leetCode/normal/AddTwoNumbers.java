package normal;

import dependency.ListNode;

/**
 * @ClassName: AddTwoNumbers
 * @Description: 2. 两数相加
 * @Author: zhaooo
 * @Date: 2024/02/27 11:07
 */
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        while (l1 != null || l2 != null || carry != 0) {
            int tmp = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
            carry = tmp / 10;
            cur.next = new ListNode(tmp % 10);
            cur = cur.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        return dummy.next;
    }
}
