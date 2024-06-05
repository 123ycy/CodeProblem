package normal;

import dependency.TreeNode;

/**
 * @ClassName: IncreasingOrderSearchTree
 * @Description: 897. 递增顺序搜索树
 * @Author: zhaooo
 * @Date: 2023/11/26 14:37
 */
public class IncreasingOrderSearchTree {
    TreeNode pre;

    public TreeNode increasingBST(TreeNode root) {
        pre = new TreeNode(0);
        TreeNode ans = pre;
        dfs(root);
        return ans.right;
    }

    void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        // 先断链
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = null;
        root.right = null;
        dfs(left);
        // 再连接
        pre.right = root;
        pre = root;
        dfs(right);
    }


    void dfs2(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs2(root.left);
        // root.left已经访问过了，置为null
        root.left = null;
        // pre.right已经访问过了，置为root
        pre.right = root;
        pre = root;
        dfs2(root.right);
    }
}
