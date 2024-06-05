package normal;

import dependency.TreeNode;

/**
 * @ClassName: AddOneRowToTree
 * @Description: 623. 在二叉树中增加一行
 * @Author: zhaooo
 * @Date: 2022/8/6/15:58
 */
public class AddOneRowToTree {
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        return dfs(root, val, depth, true);
    }

    public TreeNode dfs(TreeNode root, int val, int depth, boolean left) {
        if (depth == 1) {
            TreeNode node = new TreeNode(val);
            if (left) {
                node.left = root;
            } else {
                node.right = root;
            }
            return node;
        }
        if (root == null) {
            return null;
        }
        root.left = dfs(root.left, val, depth - 1, true);
        root.right = dfs(root.right, val, depth - 1, false);
        return root;
    }
}
