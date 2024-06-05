package normal;

import dependency.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: SymmetricTree
 * @Description: 101. 对称二叉树
 * @Author: zhaooo
 * @Date: 2024/02/10 09:12
 */
public class SymmetricTree {
    /**
     * dfs
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        return dfs(root.left, root.right);
    }

    boolean dfs(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null || node1.val != node2.val) {
            return false;
        }
        return dfs(node1.left, node2.right) && dfs(node1.right, node2.left);
    }

    /**
     * bfs
     * @param root
     * @return
     */
    public boolean isSymmetric2(TreeNode root) {
        // 或者使用LinkedList，LinkedList可以保存null
        Deque<TreeNode> deque1 = new ArrayDeque<>(), deque2 = new ArrayDeque<>();
        if ((root.left == null) != (root.right == null)) {
            return false;
        }
        if (root.left == null) {
            return true;
        }
        deque1.addLast(root.left);
        deque2.addLast(root.right);
        while (!deque1.isEmpty()) {
            TreeNode node1 = deque1.removeFirst();
            TreeNode node2 = deque2.removeFirst();
            if (node1.val != node2.val || (node1.left == null) != (node2.right == null) || (node1.right == null) != (node2.left == null)) {
                return false;
            }
            if (node1.left != null) {
                deque1.addLast(node1.left);
                deque2.addLast(node2.right);
            }
            if (node1.right != null) {
                deque1.addLast(node1.right);
                deque2.addLast(node2.left);
            }
        }
        return true;
    }
}
