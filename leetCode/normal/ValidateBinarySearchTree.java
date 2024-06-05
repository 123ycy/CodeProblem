package normal;

import dependency.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @ClassName: ValidateBinarySearchTree
 * @Description: 98. 验证二叉搜索树
 * @Author: zhaooo
 * @Date: 2022/8/25/15:55
 */
public class ValidateBinarySearchTree {
    long pre = Long.MIN_VALUE;

    /**
     * 中序遍历-递归
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 访问左子树
        if (!isValidBST(root.left)) {
            return false;
        }
        // 访问当前节点：如果当前节点小于等于中序遍历的前一个节点，说明不满足BST，返回 false；否则继续遍历。
        if (root.val <= pre) {
            return false;
        }
        pre = root.val;
        // 访问右子树
        return isValidBST(root.right);
    }

    /**
     * 中序遍历-记录值
     * @param root
     * @return
     */
    public boolean isValidBST2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inOder(root, res);
        Integer[] resArr = res.toArray(new Integer[0]);
        for (int i = 1; i < res.size(); i++) {
            if (resArr[i - 1] >= resArr[i]) {
                return false;
            }
        }
        return true;
    }

    public void inOder(TreeNode treeNode, List<Integer> res) {
        if (treeNode == null) {
            return;
        }
        inOder(treeNode.left, res);
        res.add(treeNode.val);
        inOder(treeNode.right, res);
    }

    /**
     * 中序遍历-栈非递归
     * @param root
     * @return
     */
    public boolean isValidBST3(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        long inorder = Long.MIN_VALUE;

        // 栈实现中序遍历
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.addLast(root);
                root = root.left;
            }
            root = stack.removeLast();
            // 如果中序遍历得到的节点的值小于等于前一个 inorder，说明不是二叉搜索树
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }
}
