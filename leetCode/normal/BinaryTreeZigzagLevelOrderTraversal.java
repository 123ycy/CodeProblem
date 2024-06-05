package normal;

import dependency.TreeNode;

import java.util.*;

/**
 * @ClassName: BinaryTreeZigzagLevelOrderTraversal
 * @Description: 103. 二叉树的锯齿形层序遍历
 * @Author: zhaooo
 * @Date: 2023/07/27 14:06
 */
public class BinaryTreeZigzagLevelOrderTraversal {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        boolean flag = true;
        while (!queue.isEmpty()) {
            int len = queue.size();
            Integer[] tmp = new Integer[len];
            for (int i = 0; i < len; i++) {
                TreeNode node = queue.remove();
                // 如果初始化tmp = new ArrayList<>(len);
                // tmp.add(flag ? i : len - i - 1,node.val); 或者 tmp.set(flag ? i : len - i - 1,node.val); 都会报错
                // 因为这个方法的作用是在列表的指定位置插入或者修改一个元素，但是要求这个位置不能超过列表的当前大小。
                // 当前列表在创建时就算指定initialCapacity为len，size在初始化时依旧是0
                tmp[flag ? i : len - i - 1] = node.val;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            flag = !flag;
            ret.add(Arrays.asList(tmp));
        }
        return ret;
    }

    /**
     * dfs 若为奇数层则头插
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        dfs(0, root, ret);
        return ret;
    }

    void dfs(int depth, TreeNode root, List<List<Integer>> ret) {
        if (root == null) {
            return;
        }
        if (depth >= ret.size()) {
            ret.add(new LinkedList<>());
        }
        List<Integer> cur = ret.get(depth);
        // 若为奇数层则头插
        cur.add((depth & 1) > 0 ? 0 : cur.size(), root.val);
        dfs(depth + 1, root.left, ret);
        dfs(depth + 1, root.right, ret);
    }
}
