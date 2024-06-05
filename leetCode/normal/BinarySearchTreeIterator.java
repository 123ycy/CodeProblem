package normal;

import dependency.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: BinarySearchTreeIterator
 * @Description: 173. 二叉搜索树迭代器
 * @Author: zhaooo
 * @Date: 2023/07/26 13:41
 */
public class BinarySearchTreeIterator {
    class BSTIterator {

        Deque<TreeNode> stack;

        public BSTIterator(TreeNode root) {
            stack = new ArrayDeque<>();
            while (root != null) {
                stack.addLast(root);
                root = root.left;
            }
        }

        public int next() {
            TreeNode cur = stack.removeLast();
            int val = cur.val;
            cur = cur.right;
            while (cur != null) {
                stack.addLast(cur);
                cur = cur.left;
            }
            return val;
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }
}
