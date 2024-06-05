package normal;

import dependency.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * @ClassName: KthSmallestElementInABst
 * @Description: 230. 二叉搜索树中第K小的元素
 * @Author: zhaooo
 * @Date: 2023/7/28/9:21
 */
public class KthSmallestElementInABst {
    /**
     * 优先队列
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        // 大根堆
        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> b - a);
        Deque<TreeNode> d = new ArrayDeque<>();
        d.addLast(root);
        while (!d.isEmpty()) {
            TreeNode node = d.pollFirst();
            // 堆的大小为k
            if (q.size() < k) {
                q.add(node.val);
                // 小于堆顶
            } else if (q.peek() > node.val) {
                q.poll();
                q.add(node.val);
            }
            if (node.left != null) {
                d.addLast(node.left);
            }
            if (node.right != null) {
                d.addLast(node.right);
            }
        }
        return q.peek();
    }

    /**
     * 栈 中序
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest2(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null) {
            stack.addLast(root);
            root = root.left;
        }
        while (true) {
            TreeNode tmp = stack.removeLast();
            if (--k == 0) {
                return tmp.val;
            }
            tmp = tmp.right;
            while (tmp != null) {
                stack.addLast(tmp);
                tmp = tmp.left;
            }
        }
    }

    /**
     * 栈 中序
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest3(TreeNode root, int k) {
        Deque<TreeNode> d = new ArrayDeque<>();
        while (root != null || !d.isEmpty()) {
            while (root != null) {
                d.addLast(root);
                root = root.left;
            }
            root = d.removeLast();
            if (--k == 0) {
                return root.val;
            }
            root = root.right;
        }
        return -1;
    }

    /**
     * 递归
     */
    int cnt = 0;
    int ans = 0;

    public int kthSmallest4(TreeNode root, int k) {
        dfs(root, k);
        return ans;
    }

    public void dfs(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        // 找到当前子树最小
        dfs(root.left, k);
        // 每次能确定未访问的最小值
        cnt++;
        if (cnt == k) {
            ans = root.val;
            return;
        }
        dfs(root.right, k);
    }
}
