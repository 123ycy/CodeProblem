package normal;

import dependency.TreeNode;


/**
 * @ClassName: CountCompleteTreeNodes
 * @Description: 222. 完全二叉树的节点个数
 * @Author: zhaooo
 * @Date: 2023/07/26 14:06
 */
public class CountCompleteTreeNodes {
    /**
     * 递归
     *
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }

    /**
     * 二分
     *
     * @param root
     * @return
     */
    public int countNodes2(TreeNode root) {
        // high是树高，curDepth当前访问层数，width最后一层宽度，要加上的宽度subWidth
        int high = getDepth(root), curDepth = 0, width = 0, subWidth = 1 << (high - 1);
        if (high < 2) {
            return high;
        }
        // 二分 找到最后一个节点位置
        while (curDepth != high) {
            // 每次取一个子树
            subWidth >>= 1;
            // 左右子树高度相同，最后一个节点在右子树
            if (high == ++curDepth + getDepth(root.right)) {
                root = root.right;
                // 最后一层长度加上该左子树的最后一层长度
                width += subWidth;
            } else {
                // 最后一个节点在左子树
                root = root.left;
            }
        }
        // 等价于 (1 << (high - 1)) -1 + width + 1
        // (1 << (high - 1)) -1 是 high -1 层完全二叉树的个数
        // width 最后一层除最后一个节点的宽度
        // 1 是最后一个节点
        return (1 << (high - 1)) + width;
    }

    int getDepth(TreeNode root) {
        int ret = 0;
        while (root != null) {
            ret++;
            root = root.left;
        }
        return ret;
    }
}
