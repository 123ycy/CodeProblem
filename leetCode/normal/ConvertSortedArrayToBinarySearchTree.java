package normal;

import dependency.TreeNode;

/**
 * @ClassName: ConvertSortedArrayToBinarySearchTree
 * @Description: 108. 将有序数组转换为二叉搜索树
 * @Author: zhaooo
 * @Date: 2023/8/16/9:25
 */
public class ConvertSortedArrayToBinarySearchTree {
    public TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    TreeNode build(int[] nums, int l, int r) {
        if (l > r) {
            return null;
        }
        if (l == r) {
            return new TreeNode(nums[l]);
        }
        int mid = (l + r) >> 1;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = build(nums, l, mid - 1);
        root.right = build(nums, mid + 1, r);
        return root;
    }
}
