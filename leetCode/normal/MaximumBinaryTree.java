package normal;

import dependency.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: MaximumBinaryTree
 * @Description: 654. 最大二叉树
 * @Author: zhaooo
 * @Date: 2022/8/20/15:28
 */
public class MaximumBinaryTree {
    /**
     * 递归（暴力）
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return dfs(nums, 0, nums.length);
    }

    public TreeNode dfs(int[] nums, int l, int r) {
        if (l == r) {
            return null;
        }
        int max = l;
        for (int i = l; i < r; i++) {
            if (nums[i] > nums[max]) {
                max = i;
            }
        }
        TreeNode node = new TreeNode(nums[max]);
        node.left = dfs(nums, l, max);
        node.right = dfs(nums, max + 1, r);
        return node;
    }

    /**
     * 单调栈
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree2(int[] nums) {
        // 栈顶为最小值
        Deque<TreeNode> stack = new ArrayDeque<>();
        for (int num : nums) {
            TreeNode node = new TreeNode(num);
            while (!stack.isEmpty() && num > stack.peek().val) {
                // 比栈顶元素大说明当前num是父节点，故已经访问过的栈顶是左孩子
                node.left = stack.pop();
            }
            // 当前num比栈顶元素小
            if (!stack.isEmpty()) {
                // 即栈顶元素的右孩子暂时为该num，后面出现小于当前栈顶且大于该num的值时，右孩子改变
                stack.peek().right = node;
            }
            // 当前num比栈顶元素大或者栈为空 入栈
            stack.push(node);
        }
        // 返回栈底的元素，也就是最大值
        // 此方法为双端队列的方法，严格意义并不是栈的方法
        // 严格按照栈的写法应该弹出上面的所有元素，才能取出最底下的元素
        // 就像倒水一样，先倒上面的，才能倒出下面的
        return stack.peekLast();
    }

    /**
     * 数组优化单调栈
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree3(int[] nums) {
        TreeNode[] stack = new TreeNode[nums.length];
        int index = 0;
        for (int num : nums) {
            TreeNode node = new TreeNode(num);
            while (index != 0 && num > stack[index - 1].val) {
                node.left = stack[--index];
            }
            if (index != 0) {
                stack[index - 1].right = node;
            }
            stack[index++] = node;
        }
        // 返回栈底的元素，也就是最大值
        return stack[0];
    }
}