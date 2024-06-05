package normal;

import dependency.TreeNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @ClassName: ConstructBinaryTreeFromPreorderAndInorderTraversal
 * @Description: 105. 从前序与中序遍历序列构造二叉树
 * @Author: zhaooo
 * @Date: 2023/07/23 16:35
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    /**
     * 递归
     */
    private Map<Integer, Integer> indexMap;

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preLeft, int preRight, int inLeft, int inRight) {
        if (inLeft > inRight) {
            return null;
        }
        int inRoot = indexMap.get(preorder[preLeft]);
        TreeNode node = new TreeNode(preorder[preLeft]);
        int leftTreeSize = inRoot - inLeft;
        node.left = myBuildTree(preorder, inorder, preLeft + 1, preLeft + leftTreeSize, inLeft, inRoot - 1);
        node.right = myBuildTree(preorder, inorder, preLeft + leftTreeSize + 1, preRight, inRoot + 1, inRight);
        return node;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }

    /**
     * 迭代
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }
        Stack<TreeNode> roots = new Stack<>();
        // 下一个访问位置
        int pre = 0;
        // 下一个访问位置
        int in = 0;
        // 先序遍历第一个值作为根节点
        TreeNode curRoot = new TreeNode(preorder[pre]);
        TreeNode root = curRoot;
        roots.push(curRoot);
        pre++;
        // 遍历前序遍历的数组
        while (pre < preorder.length) {
            // 出现了当前节点的值和中序遍历数组的值相等，寻找是谁的右子树
            if (curRoot.val == inorder[in]) {
                // curRoot.val == inorder[in]时：
                //                    peek/cur  pre
                // preorder =>（根（根   左        右）（根左右））
                //                in
                // inorder => （（ 左 根右）根（左根右））
                // 此时栈内保存当前子树的根到的最左节点路径
                // preorder的根到左和inorder的左到根长度一致
                while (!roots.isEmpty() && roots.peek().val == inorder[in]) {
                    // 先curRoot = roots.peek() 再 pop()
                    curRoot = roots.peek();
                    // pop相当于pre--
                    roots.pop();
                    in++;
                }
                // curRoot.val != inorder[in]时：
                //             peek cur     pre
                // preorder =>（根 （ 根  左  右）（根左右））
                //                   in
                // inorder => （（左根 右 ）根（左根右））
                // 不相同或者栈为空都表示左到根的路径访问完了，找到当前inorder元素是谁的右孩子
                // inorder[in]不能是右子树，否则可能会让右子树的左节点当根
                // peek cur pre                  in
                //       根（左）（根左右）  （左）根（左根右）
                curRoot.right = new TreeNode(preorder[pre]);
                // 左侧已经访问完了，更新子树即改变curRoot
                curRoot = curRoot.right;
                roots.push(curRoot);
                pre++;
            } else {
                // 否则的话就一直作为左子树
                curRoot.left = new TreeNode(preorder[pre]);
                curRoot = curRoot.left;
                roots.push(curRoot);
                pre++;
            }
        }
        return root;
    }
}
