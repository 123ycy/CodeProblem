package normal;

import dependency.TreeNode;

/**
 * @ClassName: BinaryTreePruning
 * @Description: 814. 二叉树剪枝
 * @Author: zhaooo
 * @Date: 2022/7/21/12:54
 */
public class BinaryTreePruning {
    /**
     * 单独遍历是否存在1
     * @param root
     * @return
     */
    public TreeNode pruneTree(TreeNode root) {
        if (!containsOne(root)) {
            return null;
        }
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        return root;
    }

    public boolean containsOne(TreeNode root) {
        if (root == null) {
            return false;
        }
        return root.val == 1 || containsOne(root.left) || containsOne(root.right);
    }

    /**
     * 后序遍历
     * @param root
     * @return
     */
    public TreeNode pruneTree2(TreeNode root) {
        if (root == null) {
            return null;
        }
        root.left = pruneTree2(root.left);
        root.right = pruneTree2(root.right);
        // 递归赋值为null，root.xxx == null表示不存在或者原来值为0
        if (root.left == null && root.right == null && root.val == 0) {
            return null;
        }
        return root;
    }
}
