package normal;

import dependency.TreeNode;

/**
 * @ClassName: LowestCommonAncestorOfABinaryTree
 * @Description: 236. 二叉树的最近公共祖先
 * @Author: zhaooo
 * @Date: 2024/01/31 19:17
 */
public class LowestCommonAncestorOfABinaryTree {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return selfOrAncestor(root, p, q);
    }

    TreeNode selfOrAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 边界条件
        // 空节点或者该节点是公共祖先的字节的
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        // 作用子树分别寻找
        TreeNode left = selfOrAncestor(root.left, p, q);
        TreeNode right = selfOrAncestor(root.right, p, q);

        // 2种写法
        // 一边找到了，一边没找到 即该树不含目标p或q，返回另一颗树
        // if(null == left) return right;
        // if(null == right) return left;
        // 如果在左右子树分别找到了p和q，则说明root是它们两个的最近公共祖先。
        // return root;

        // 分散在左右
        if (right != null && left != null) {
            return root;
        }
        // 一边找到了，一边没找到 即该树不含目标p或q，返回另一颗树
        return right == null ? left : right;
    }
}
