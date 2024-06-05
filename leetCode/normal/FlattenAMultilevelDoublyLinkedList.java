package normal;

/**
 * @ClassName: FlattenAMultilevelDoublyLinkedList
 * @Description: 430. 扁平化多级双向链表
 * @Author: zhaooo
 * @Date: 2023/10/27 15:29
 */
public class FlattenAMultilevelDoublyLinkedList {
    public Node flatten(Node head) {
        dfs(head);
        return head;
    }

    Node dfs(Node head) {
        Node node = head, pre = null, next = null;
        while (node != null) {
            next = node.next;
            if (node.child != null) {
                Node tail = dfs(node.child);
                node.next = node.child;
                node.child.prev = node;
                node.child = null;
                if (next != null) {
                    tail.next = next;
                    next.prev = tail;
                    // 最后一个节点有child时，tail应该是child的tail
                } else {
                    return tail;
                }
            }
            pre = node;
            node = next;
        }
        return pre;
    }
}

class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
}