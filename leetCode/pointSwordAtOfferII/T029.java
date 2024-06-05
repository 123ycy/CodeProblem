package pointSwordAtOfferII;

/**
 * @ClassName: T029
 * @Description: 剑指 Offer II 029. 排序的循环链表
 * @Author: zhaooo
 * @Date: 2022/6/18/16:32
 */
public class T029 {
    public Node insert(Node head, int insertVal) {
        if (head == null) {
            head = new Node(insertVal);
            head.next = head;
            return head;
        }
        if (head.next == head) {
            head.next = new Node(insertVal);
            head.next.next = head;
            return head;
        }
        Node pre = head;
        Node next = head.next;
        Node max = head;
        while (true) {
            // max为有序链表尾
            if (pre.val >= max.val) {
                max = pre;
            }
            if (pre.val <= insertVal && insertVal <= next.val) {
                pre.next = new Node(insertVal);
                pre.next.next = next;
                return head;
            }
            // 首尾之间没有符合要求的边,即对应边为是尾到首
            if (next == head) {
                Node newNode = new Node(insertVal);
                newNode.next = max.next;
                max.next = newNode;
                return head;
            }
            pre = next;
            next = next.next;
        }
    }

    class Node {
        public int val;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }
}
