package normal;

import dependency.TreeNode;

/**
 * @ClassName: PathSum
 * @Description: 112. 路径总和
 * @Author: zhaooo
 * @Date: 2023/07/25 11:57
 */
public class PathSum {

    /**
     * 递归
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        boolean res = false;
        if (root.left != null) {
            // 相当于 res |= hasPathSum(root.left, targetSum - root.val);
            res = hasPathSum(root.left, targetSum - root.val);
        }
        // 剪枝
        if (!res && root.right != null) {
            res = hasPathSum(root.right, targetSum - root.val);
        }
        return res;
    }

    /**
     * 递归
     *
     * @param root
     * @param targetSum
     * @return
     */
    public static boolean hasPathSum2(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null && targetSum - root.val == 0) {
            return true;
        }
        return hasPathSum2(root.left, targetSum - root.val) || hasPathSum2(root.right, targetSum - root.val);
    }
}
