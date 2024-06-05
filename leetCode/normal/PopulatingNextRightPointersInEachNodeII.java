package normal;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName: PopulatingNextRightPointersInEachNodeII
 * @Description: 117. 填充每个节点的下一个右侧节点指针 II
 * @Author: zhaooo
 * @Date: 2023/07/24 15:21
 */
public class PopulatingNextRightPointersInEachNodeII {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * 递归
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null || (root.right == null && root.left == null)) {
            return root;
        }
        // 每次访问当前层时，利用当前层的next，递归建立下一层的next
        if (root.left != null && root.right != null) {
            root.left.next = root.right;
            root.right.next = getNextNoNullChild(root);
        }
        if (root.left == null) {
            root.right.next = getNextNoNullChild(root);
        }
        if (root.right == null) {
            root.left.next = getNextNoNullChild(root);
        }

        // 这里要注意：先递归右子树，相当于头插入建立链表，这样从当前节点向后遍历时不会有没有连接的节点
        root.right = connect(root.right);
        root.left = connect(root.left);

        return root;
    }

    /**
     * 一路向右找到有子节点的根节点
     */
    private static Node getNextNoNullChild(Node head) {
        while (head.next != null) {
            if (head.next.left != null) {
                return head.next.left;
            }
            if (head.next.right != null) {
                return head.next.right;
            }
            head = head.next;
        }
        return null;
    }

    /**
     * 队列 层序遍历
     *
     * @param root
     * @return
     */
    public Node connect2(Node root) {
        // LinkedList的实现可以向队列添加null
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node tmp = queue.poll();
            if (tmp == null) {
                continue;
            }
            tmp.next = queue.peek();
            if (tmp.left != null) {
                queue.add(tmp.left);
            }
            if (tmp.right != null) {
                queue.add(tmp.right);
            }
            // 添加null表示一层访问完
            if (tmp.next == null) {
                queue.add(null);
            }
        }
        return root;
    }
}
