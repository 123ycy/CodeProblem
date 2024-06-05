package normal;

import dependency.TreeNode;

import java.util.*;

/**
 * @ClassName: BinaryTreeRightSideView
 * @Description: 199. 二叉树的右视图
 * @Author: zhaooo
 * @Date: 2023/07/27 12:24
 */
public class BinaryTreeRightSideView {

    /**
     * 递归
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        dfs(root, 1, ret);
        return ret;
    }

    private void dfs(TreeNode root, int depth, List<Integer> ret) {
        if (root == null) {
            return;
        }
        if (depth > ret.size()) {
            ret.add(root.val);
        }
        // 先递归右子树
        dfs(root.right, depth + 1, ret);
        dfs(root.left, depth + 1, ret);
    }

    /**
     * 层序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = null;
            for (int i = 0; i < queue.size(); i++) {
                node = queue.remove();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            // 将当前层的最后一个节点放入结果列表
            res.add(node.val);
        }
        return res;
    }
}