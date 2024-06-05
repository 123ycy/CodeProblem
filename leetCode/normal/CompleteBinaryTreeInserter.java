package normal;

import dependency.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CompleteBinaryTreeInserter
 * @Description: 919. 完全二叉树插入器
 * @Author: zhaooo
 * @Date: 2023/11/22 17:17
 */
public class CompleteBinaryTreeInserter {
    class CBTInserter {
        // 指向父节点层可以插入的位置
        int idx;
        // 树高
        int level;
        TreeNode root;
        // 父节点层
        List<TreeNode> cur;
        // 插入层
        List<TreeNode> next;

        public CBTInserter(TreeNode root) {
            idx = 0;
            level = 1;
            this.root = root;
            cur = new ArrayList<>(1);
            next = new ArrayList<>(2);
            cur.add(root);
            while (true) {
                for (; idx < cur.size(); idx++) {
                    TreeNode node = cur.get(idx);
                    if (node.left == null) {
                        return;
                    }
                    next.add(node.left);
                    if (node.right == null) {
                        return;
                    }
                    next.add(node.right);
                }
                idx = 0;
                cur = next;
                next = new ArrayList<>(1 << ++level);
            }
        }

        public int insert(int val) {
            TreeNode parent = cur.get(idx);
            TreeNode child = new TreeNode(val);
            next.add(child);
            if (parent.left == null) {
                parent.left = child;
            } else {
                parent.right = child;
                if (++idx == cur.size()) {
                    idx = 0;
                    cur = next;
                    next = new ArrayList<>(1 << ++level);
                }
            }
            return parent.val;
        }

        public TreeNode get_root() {
            return root;
        }
    }
}
