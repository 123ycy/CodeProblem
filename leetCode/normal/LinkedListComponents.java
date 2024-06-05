package normal;

import dependency.ListNode;

/**
 * @ClassName: LinkedListComponents
 * @Description: 817. 链表组件
 * @Author: zhaooo
 * @Date: 2022/10/12/15:19
 */
public class LinkedListComponents {
    /**
     * 简单模拟
     * @param head
     * @param nums
     * @return
     */
    public int numComponents(ListNode head, int[] nums) {
        boolean[] map = new boolean[(int) 1e4 + 10];
        int ret = 0;
        for (int num : nums) {
            map[num] = true;
        }
        ListNode cur = head;
        while (cur != null) {
            if (map[cur.val]) {
                ListNode tmp = cur.next;
                while (tmp != null && map[tmp.val]) {
                    tmp = tmp.next;
                }
                ret++;
                cur = tmp;
            } else {
                cur = cur.next;
            }
        }
        return ret;
    }
}
