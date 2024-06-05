package normal;

import dependency.TreeNode;

/**
 * @ClassName: TrimABinarySearchTree
 * @Description: 669. 修剪二叉搜索树
 * @Author: zhaooo
 * @Date: 2022/9/10/11:15
 */
public class TrimABinarySearchTree {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }
        if (root.val < low) {
            return trimBST(root.right, low, high);
        }
        if (root.val > high) {
            return trimBST(root.left, low, high);
        }
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        return root;
    }
}
