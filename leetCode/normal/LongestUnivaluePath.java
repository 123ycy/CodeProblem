package normal;

import dependency.TreeNode;

/**
 * @ClassName: LongestUnivaluePath
 * @Description: 687. 最长同值路径
 * @Author: zhaooo
 * @Date: 2022/9/2/8:39
 */
public class LongestUnivaluePath {
    /**
     * 暴力dfs
     * 分解为多个功能
     * @param root
     * @return
     */
    public int longestUnivaluePath(TreeNode root) {
        return dfs(root);
    }

    // 以该节点为根的最大长度
    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = sum(root.left, root.val);
        int r = sum(root.right, root.val);
        return Math.max(l + r, Math.max(longestUnivaluePath(root.left), longestUnivaluePath(root.right)));
    }

    // 从该节点向下值为val的最大长度
    int sum(TreeNode root, int val) {
        if (root == null || root.val != val) {
            return 0;
        }
        return 1 + Math.max(sum(root.left, val), sum(root.right, val));
    }

    int ret = 0;

    /**
     * 模板解法
     * @param root
     * @return
     */
    public int longestUnivaluePath2(TreeNode root) {
        // 不能return dfs2(root) 若则表示是包含根节点的最大值
        dfs2(root);
        return ret;
    }

    // 以当前节点为根向下的最大同值长度
    int dfs2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 递归（递归过程中求不同根节点的最大值）
        int l = dfs2(root.left);
        int r = dfs2(root.right);
        // 如果存在左子节点和根节点同值，更新左最长路径;否则左最长路径为0
        if (root.left != null && root.left.val == root.val) {
            l++;
        } else {
            l = 0;
        }
        if (root.right != null && root.right.val == root.val) {
            r++;
        } else {
            r = 0;
        }
        ret = Math.max(ret, l + r);
        return Math.max(l, r);
    }
}
