package normal;

import dependency.TreeNode;

/**
 * @ClassName: InorderSuccessorInBst
 * @Description: 285. 二叉搜索树中的中序后继
 * LCR 053. 二叉搜索树中的中序后继
 * @Author: zhaooo
 * @Date: 2023/11/27 13:33
 */
public class InorderSuccessorInBst {
    TreeNode pre = null;
    TreeNode ans = null;

    /**
     * 递归
     *
     * @param root
     * @param p
     * @return
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }
        inorderSuccessor(root.left, p);
        // 找到前一个是p的节点
        if (p == pre) {
            ans = root;
        }
        pre = root;
        inorderSuccessor(root.right, p);
        // 遍历完整个子树
        return ans;
    }

    /**
     * 遍历
     *
     * @param root
     * @param p
     * @return
     */
    public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
        while (root != null) {
            // 找到比p大的节点中最小的
            if (root.val > p.val) {
                ans = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return ans;
    }
}
