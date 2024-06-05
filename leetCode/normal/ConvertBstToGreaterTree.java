package normal;

import dependency.TreeNode;

/**
 * @ClassName: ConvertBstToGreaterTree
 * @Description: 538. 把二叉搜索树转换为累加树
 * @Author: zhaooo
 * @Date: 2023/11/28 11:11
 */
public class ConvertBstToGreaterTree {
    int sum = 0;

    /**
     * 全局变量
     *
     * @param root
     * @return
     */
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }
        convertBST(root.right);
        sum += root.val;
        root.val = sum;
        convertBST(root.left);
        return root;
    }

    /**
     * 局部变量
     *
     * @param root
     * @return
     */
    public TreeNode convertBST2(TreeNode root) {
        dfs(root, 0);
        return root;
    }

    private int dfs(TreeNode root, int parentVal) {
        if (root == null)
            return parentVal;
        root.val += dfs(root.right, parentVal);
        return dfs(root.left, root.val);
    }
}
