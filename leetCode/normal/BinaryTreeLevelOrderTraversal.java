package normal;

import dependency.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @ClassName: BinaryTreeLevelOrderTraversal
 * @Description: 102. 二叉树的层序遍历
 * @Author: zhaooo
 * @Date: 2024/03/16 14:10
 */
public class BinaryTreeLevelOrderTraversal {
    /**
     * bfs
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.removeFirst();
                tmp.add(node.val);
                if (node.left != null) {
                    queue.addLast(node.left);
                }
                if (node.right != null) {
                    queue.addLast(node.right);
                }
            }
            res.add(tmp);
        }
        return res;
    }

    /**
     * dfs
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        dfs(root, 0, ans);
        return ans;
    }

    public void dfs(TreeNode root, int depth, List<List<Integer>> ans) {
        // depth表示当前深度 从0开始
        if (depth == ans.size()) {
            ans.add(new ArrayList<>());
        }
        List<Integer> list = ans.get(depth);
        list.add(root.val);
        if (root.left != null) {
            dfs(root.left, depth + 1, ans);
        }
        if (root.right != null) {
            dfs(root.right, depth + 1, ans);
        }
    }
}
