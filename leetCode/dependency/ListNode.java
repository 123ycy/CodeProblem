package dependency;

/**
 * @ClassName : ListNode
 * @Description: 链表元素
 * @Author zhaooo
 * @Date 2021/3/18/16:48
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}