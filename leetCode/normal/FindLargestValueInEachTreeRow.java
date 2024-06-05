package normal;

import dependency.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @ClassName: FindLargestValueInEachTreeRow
 * @Description: 515. 在每个树行中找最大值
 * @Author: zhaooo
 * @Date: 2022/6/24/12:37
 */
public class FindLargestValueInEachTreeRow {
    List<Integer> res = new ArrayList<>();

    /**
     * dfs
     * @param root
     * @return
     */
    public List<Integer> largestValues(TreeNode root) {
        dfs(root, 0);
        return res;
    }

    public void dfs(TreeNode node, int height) {
        if (node == null) {
            return;
        }
        if (res.size() == height) {
            res.add(node.val);
        }
        res.set(height, Math.max(res.get(height), node.val));
        dfs(node.left, height + 1);
        dfs(node.right, height + 1);
    }

    /**
     * bfs
     * @param root
     * @return
     */
    public List<Integer> largestValues2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            int maxVal = Integer.MIN_VALUE;
            while (len > 0) {
                len--;
                TreeNode t = queue.poll();
                maxVal = Math.max(maxVal, t.val);
                if (t.left != null) {
                    queue.offer(t.left);
                }
                if (t.right != null) {
                    queue.offer(t.right);
                }
            }
            res.add(maxVal);
        }
        return res;
    }
}
