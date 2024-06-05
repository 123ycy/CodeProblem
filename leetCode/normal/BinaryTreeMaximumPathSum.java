package normal;

import dependency.TreeNode;

/**
 * @ClassName: BinaryTreeMaximumPathSum
 * @Description: 124. 二叉树中的最大路径和
 * @Author: zhaooo
 * @Date: 2022/9/3/16:54
 */
public class BinaryTreeMaximumPathSum {
    // 初始化为最小值
    int ret = Integer.MIN_VALUE;

    /**
     * dfs
     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs(root);
        return ret;
    }

    // 表示以root结尾的路径和最大值
    int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = dfs(root.left);
        int r = dfs(root.right);
        // 每次只需要判断包含root路径的最大值 就能判断整个树的所有情况
        ret = Math.max(root.val + Math.max(l, 0) + Math.max(r, 0), ret);
        return Math.max(root.val, Math.max(l + root.val, r + root.val));
        // 或者
        // int l = Math.max(dfs(root.left), 0);
        // int r = Math.max(dfs(root.right), 0);
        // ret = Math.max(root.val + l + r, ret);
        // return Math.max(l, r) + root.val;
    }
}
