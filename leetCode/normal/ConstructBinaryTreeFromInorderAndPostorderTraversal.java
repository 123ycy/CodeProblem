package normal;

import dependency.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ConstructBinaryTreeFromInorderAndPostorderTraversal
 * @Description: 106. 从中序与后序遍历序列构造二叉树
 * @Author: zhaooo
 * @Date: 2023/07/22 18:12
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    private Map<Integer, Integer> map;

    public TreeNode buildTree(int[] inorder, int[] postorder, int inLeft, int inRight, int postLeft, int postRight) {
        if (inLeft > inRight) {
            return null;
        }
        int inIdx = map.get(postorder[postRight]);
        int rightLen = inRight - inIdx;
        TreeNode root = new TreeNode(postorder[postRight]);
        root.right = buildTree(inorder, postorder, inIdx + 1, inRight, postLeft + rightLen, postRight - 1);
        root.left = buildTree(inorder, postorder, inLeft, inIdx - 1, postLeft, postRight - 1 - rightLen);
        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }
        return buildTree(inorder, postorder, 0, n - 1, 0, n - 1);
    }
}
