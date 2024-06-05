package normal;

import dependency.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PrintBinaryTree
 * @Description: 655. 输出二叉树
 * @Author: zhaooo
 * @Date: 2022/8/22/11:12
 */
public class PrintBinaryTree {
    int maxDepth;
    List<List<String>> res;

    public List<List<String>> printTree(TreeNode root) {
        maxDepth = getMaxDepth(root);
        res = new ArrayList<>();
        for (int i = 0; i < maxDepth; i++) {
            List<String> tmp = new ArrayList<>();
            for (int j = 0; j < (1 << maxDepth) - 1; j++) {
                tmp.add("");
            }
            res.add(tmp);
        }
        dfs(root, 0, 0, (1 << maxDepth) - 2);
        return res;
    }

    void dfs(TreeNode node, int depth, int l, int r) {
        if (node == null) {
            return;
        }
        int mid = (l + r) >> 1;
        res.get(depth).set(mid, String.valueOf(node.val));
        dfs(node.left, depth + 1, l, mid - 1);
        dfs(node.right, depth + 1, mid + 1, r);
    }

    int getMaxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getMaxDepth(root.left), getMaxDepth(root.right)) + 1;
    }
}
