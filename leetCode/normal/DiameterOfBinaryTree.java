package normal;

import dependency.TreeNode;

/**
 * @ClassName: DiameterOfBinaryTree
 * @Description: 543. 二叉树的直径
 * @Author: zhaooo
 * @Date: 2022/9/3/14:56
 */
public class DiameterOfBinaryTree {
    int res = 0;

    /**
     * 模板解法
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return res;
    }

    public int dfs(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        }
        int l = dfs(treeNode.left);
        int r = dfs(treeNode.right);
        // 更新全局变量
        res = Math.max(res, l + r);
        // 返回左右路径较长者
        return Math.max(l, r) + 1;
    }
}
