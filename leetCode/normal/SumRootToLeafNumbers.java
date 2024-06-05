package normal;

import dependency.TreeNode;

/**
 * @ClassName: SumRootToLeafNumbers
 * @Description: 129. 求根节点到叶节点数字之和
 * @Author: zhaooo
 * @Date: 2023/07/25 13:20
 */
public class SumRootToLeafNumbers {
    int res = 0;

    public int sumNumbers(TreeNode root) {
        dfs(root, 0);
        return res;
    }

    public void dfs(TreeNode root, int cur) {
        cur *= 10;
        cur += root.val;
        if (root.left == null && root.right == null) {
            res += cur;
        }
        if (root.left != null) {
            dfs(root.left, cur);
        }
        if (root.right != null) {
            dfs(root.right, cur);
        }
    }
}
