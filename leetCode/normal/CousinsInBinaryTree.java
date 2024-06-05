package normal;

import dependency.TreeNode;

/**
 * @ClassName: CousinsInBinaryTree
 * @Description: 993. 二叉树的堂兄弟节点
 * @Author: zhaooo
 * @Date: 2022/9/24/13:05
 */
public class CousinsInBinaryTree {
    TreeNode preX, preY, pre;
    int x, y, hx, hy;

    public boolean isCousins(TreeNode root, int x, int y) {
        this.x = x;
        this.y = y;
        dfs(root, 0);
        return hx == hy && preX != preY;
    }

    void dfs(TreeNode node, int h) {
        if (node == null) {
            return;
        }
        if (node.val == x) {
            preX = pre;
            hx = h;
            return;
        }
        if (node.val == y) {
            preY = pre;
            hy = h;
            return;
        }
        pre = node;
        dfs(node.left, h + 1);
        dfs(node.right, h + 1);
    }
}
